package com.customized.libs.core.libs.equals;

public class StringEquals {

    public static void main(String[] args) {
        System.out.println("StringEquals.equals(\"12345\", \"123456\") ==> " + StringEquals.equals("12345", "123456"));
        System.out.println("StringEquals.equals(\"12345\", \"12345\") ==> " + StringEquals.equals("12345", "12345"));
    }

    /**
     * 通过异或操作 1^1=0 , 1^0=1, 0^0=0，
     * 来比较每一位，如果每一位都相等的话，两个字符串肯定相等，最后存储累计异或值的变量 equal必定为 0，否则为 1
     * <p>
     * <p>
     * 符号	描述	    运算规则<br/>
     * &	与	    两个位都为1时，结果才为1<br/>
     * |	或	    两个位都为0时，结果才为0<br/>
     * ^	异或	    两个位相同为0，相异为1<br/>
     * ~	取反  	0变1，1变0<br/>
     * <<	左移	    各二进位全部左移若干位，高位丢弃，低位补0<br/>
     * >>	右移  	各二进位全部右移若干位，对无符号数，高位补0，有符号数，各编译器处理方法不一样，有的补符号位（算术右移），有的补0（逻辑右移）<br/>
     *
     * @param str0
     * @param str1
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean equals(String str0, String str1) {
        if (str0 == null || str1 == null) return false;
        if (str0.length() != str1.length()) return false;

        byte[] bytes0 = str0.getBytes();
        byte[] bytes1 = str1.getBytes();

        int result = 0;
        for (int i = 0; i < bytes1.length; i++) {
            result |= bytes0[i] ^ bytes1[i];
        }
        return result == 0;
    }
}
