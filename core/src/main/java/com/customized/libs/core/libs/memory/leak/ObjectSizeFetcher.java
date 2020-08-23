package com.customized.libs.core.libs.memory.leak;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * https://www.cnblogs.com/tesla-turing/p/11487815.html
 * <p>
 * 2. 在META-INF下面新建MANIFEST.MF文件，并且指定
 * <p>
 * Manifest-Version: 1.0
 * Premain-Class: instrumentation.test.ObjectShallowSize
 * <p>
 * 3. 通过eclipse->export->jar->next->next,然后选中定制的 MANIFEST.MF 文件，进行jar打包。
 * <p>
 * 4. 给需要使用ObjectShallowSize的工程引入该jar包，并通过代码测试对象所占内存大小：
 * <p>
 * 1 System.out.println(ObjectShallowSize.sizeOf(new ObjectA())); // 32
 * 5. 在运行调用ObjectShallowSize.sizeof的类的工程中加上刚打的jar包依赖，同时eclipse里面run configuration,在VM arguments中添加（标红部分为jar包的绝对路径）：
 * <p>
 * -javaagent:E:/software/instrumentation-sizeof.jar
 */
public class ObjectSizeFetcher {
    // instrumentation 是一个 java.lang.instrument.Instrumentation 的实例，由 JVM 自动传入
    private static Instrumentation instrumentation;

    /**
     * 这个方法先于主方法(main)执行
     *
     * @param args
     * @param inst
     */
    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    /**
     * 直接计算当前对象占用空间大小，包括当前类及超类的基本类型实例字段大小、
     * 引用类型实例字段引用大小、实例基本类型数组总占用空间、实例引用类型数组引用本身占用空间大小;
     * 但是不包括超类继承下来的和当前类声明的实例引用字段的对象本身的大小、实例引用数组引用的对象本身的大小
     *
     * @param o 需要计算内存的对象
     * @return 返回内存大小
     */
    public static long sizeOf(Object o) {
        return instrumentation.getObjectSize(o);
    }

    /**
     * 递归计算当前对象占用空间总大小，包括当前类和超类的实例字段大小以及实例字段引用对象大小
     * 注意：这个方法如果你看不懂也没关系，会用就行
     *
     * @param objP
     * @return
     * @throws IllegalAccessException
     */
    public static long fullSizeOf(Object objP) throws IllegalAccessException {
        Set<Object> visited = new HashSet<Object>();
        Deque<Object> toBeQueue = new ArrayDeque<>();
        toBeQueue.add(objP);
        long size = 0L;
        while (toBeQueue.size() > 0) {
            Object obj = toBeQueue.poll();
            //sizeOf的时候已经计基本类型和引用的长度，包括数组
            size += skipObject(visited, obj) ? 0L : sizeOf(obj);
            Class<?> tmpObjClass = obj.getClass();
            if (tmpObjClass.isArray()) {
                //[I , [F 基本类型名字长度是2
                if (tmpObjClass.getName().length() > 2) {
                    for (int i = 0, len = Array.getLength(obj); i < len; i++) {
                        Object tmp = Array.get(obj, i);
                        if (tmp != null) {
                            //非基本类型需要深度遍历其对象
                            toBeQueue.add(Array.get(obj, i));
                        }
                    }
                }
            } else {
                while (tmpObjClass != null) {
                    Field[] fields = tmpObjClass.getDeclaredFields();
                    for (Field field : fields) {
                        if (Modifier.isStatic(field.getModifiers())   //静态不计
                                || field.getType().isPrimitive()) {    //基本类型不重复计
                            continue;
                        }

                        field.setAccessible(true);
                        Object fieldValue = field.get(obj);
                        if (fieldValue == null) {
                            continue;
                        }
                        toBeQueue.add(fieldValue);
                    }
                    tmpObjClass = tmpObjClass.getSuperclass();
                }
            }
        }
        return size;
    }

    /**
     * String.intern的对象不计；计算过的不计，也避免死循环
     *
     * @param visited
     * @param obj
     * @return
     */
    static boolean skipObject(Set<Object> visited, Object obj) {
        if (obj instanceof String && obj == ((String) obj).intern()) {
            return true;
        }
        return visited.contains(obj);
    }

}