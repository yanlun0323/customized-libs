package com.customized.libs.core.libs.thread.safety;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 总结：
 * String是final修饰的类，是不可变的，所以是线程安全的。
 * <p>
 * 因为字符串是不可变的，所以是多线程安全的，同一个字符串实例可以被多个线程共享。这样便不用因为线程安全问题而使用同步。字符串自己便是线程安全的。
 * <p>
 * 因为字符串是不可变的，所以在它创建的时候HashCode就被缓存了，不需要重新计算。这就使得字符串很适合作为Map中的键，字符串的处理速度要快过其它的键对象。这就是HashMap中的键往往都使用字符串。
 * ————————————————
 * 版权声明：本文为CSDN博主「Lemon_MY」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/Lemon_MY/article/details/100634576
 *
 * @author yan
 */
public class StringThreadSafetyTest {

    public static void main(String[] args) {

        /**
         * 结论1：String对象的class描述为final，即对象内容不可变，那么当作为参数传递的时候，实际上String对象引用
         */
        AtomicReference<String> data0 = new AtomicReference<>("");
        for (int i = 0; i < 2; i++) {
            int index = i;
            new Thread(() -> {
                data0.set(data0.get() + index);
                System.out.println(data0.get());
            });
        }

        /**
         * 以下案例可证明结论1的观点，data作为函数dataString的参数，是对象引用方式，但是一旦data被改变，实际上是会创建新的String对象
         * 这里，我们可以看到，两次循环后的data+1操作均返回了同一个对象hash值，那说明此处运用了常量池。
         */
        String data = "simple-data";
        for (int i = 0; i < 2; i++) {
            dataString(data);
            System.out.println();
        }
        System.out.println("old-data => " + data);
        System.out.println("old-data-hashcode => " + Integer.toHexString(data.hashCode()));
    }

    public static void dataString(String data) {
        System.out.println("old-hashcode => " + Integer.toHexString(data.hashCode()));
        data = data + 1;
        System.out.println("new-data-hashcode ==> " + Integer.toHexString(data.hashCode()));
    }
}
