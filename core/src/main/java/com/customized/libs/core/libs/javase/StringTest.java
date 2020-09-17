package com.customized.libs.core.libs.javase;

public class StringTest {

    public static void main(String[] args) {
        System.out.println(reverseStringByRecursion("ABCD"));
        System.out.println(reverseStringByForeach("ABCD"));
    }

    /**
     * 递归分析（recursion(oString.substring(0, len - 1)) + oString.substring(0, 1)）
     * <p>
     * 将当前字符串的最前一个放到最后即可
     * <p>
     * OR
     * <p>
     * 将最后一个放到第一个，剩下的递归
     *
     * @param str
     * @return
     */
    public static String reverseStringByRecursion(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        //return reverseStringByRecursion(str.substring(1, str.length())) + str.substring(0, 1);
        return str.substring(str.length() - 1) + reverseStringByRecursion(str.substring(0, str.length() - 1));
    }


    public static String reverseStringByForeach(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }

        char[] chars = str.toCharArray();
        char[] temp = new char[chars.length];

        int index = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            temp[index++] = chars[i];
        }

        return new String(temp);
    }
}
