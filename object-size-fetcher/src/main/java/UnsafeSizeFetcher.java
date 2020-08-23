import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author yan
 */
public class UnsafeSizeFetcher {

    private final static Unsafe UNSAFE;

    // 只能通过反射获取Unsafe对象的实例
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(Boolean.TRUE);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public UnsafeSizeFetcher() {
    }

    public static void main(String[] args) {
        /*
         * Size(ObjectA) = Size(对象头(_mark)) + size(oop指针) + size(数据区)
         * Size(ObjectA) = 8 + 4 + 4(String) + 4(int) + 1(byte) + 1(byte) + 2(padding) + 4(int) + 4(ObjectB指针) + 1(byte) + 7(padding)
         * Size(ObjectA) = 40
         */

        /*
         * Size(ObjectA) = Size(对象头(_mark)) + size(oop指针) + size(排序后数据区)  =  8 + 4 + (28+4-12)  =  32.

         */
        Field[] fields = ObjectA.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + "---offSet:" + UNSAFE.objectFieldOffset(field));
        }
    }
}
