package com.customized.libs.core.libs.javase.hash;

/**
 * @author yan
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
public class StringHashCodeDemo {

    public static void main(String[] args) {
        String a0 = "Aa";
        String a1 = "BB";

        System.out.println(a0.hashCode());
        System.out.println(a1.hashCode());


        System.out.println("A".hashCode());
        System.out.println("a".hashCode());
        System.out.println("B".hashCode());

        /*
         * 对于任意两个字符串 xy 和 ab，如果它们满足 x-a=1，即第一个字符的 ASCII 码值相差为1，
         * 同时满足 b-y=31，即第二个字符的 ASCII 码值相差为 -31。
         * 那么这两个字符的 hashCode 一定相等。
         *
         * x - a = 1 && b - y = 31
         *
         * Aa 和 BB 的 HashCode 是一样的。我们把它两一排列组合，那不还是一样的吗？
         */
        String x, y, a, b;
        String left, right;
        for (int i = "A".hashCode(); i <= "z".hashCode(); i++) {
            for (int j = "A".hashCode(); j <= "z".hashCode(); j++) {
                x = String.valueOf((char) i);
                y = String.valueOf((char) j);
                left = x + y;
                a = String.valueOf((char) (i - 1));
                b = String.valueOf((char) (j + 31));
                right = a + b;
                if (right.hashCode() == left.hashCode()) {
                    System.out.printf("%s.hashCode()=%s, %s.hashCode()=%s%n",
                            left, left.hashCode(), right, right.hashCode()
                    );
                }
            }
        }
    }
}
