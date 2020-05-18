package com.customized.libs.core.libs.javase;

public class ByteDemo {


    /**
     * 最高位是符号位：1代表负数，0代表正数（byte范围在-128 到 127）
     * <p>
     * Integer ==> -2147483648, 2147483647
     * Byte ==> -128, 127
     * <p>
     * -1 ==> 11111111111111111111111111111111
     * <p>
     * 1  ==> 00000000000000000000000000000001
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(-1));

        System.out.println(String.format("%32s", Integer.toBinaryString(1)).replace(' ', '0'));
        System.out.println(String.format("%8s", Integer.toBinaryString(128)).replace(' ', '0'));


        byte _127byte = 127;
        byte _128byte = -128;
        System.out.println(String.format("%8s", Integer.toBinaryString(_127byte & 0xFF)).replace(' ', '0'));
        System.out.println(String.format("%8s", Integer.toBinaryString(_128byte & 0xFF)).replace(' ', '0'));

        System.out.println("Integer ==> " + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE);

        System.out.println("Byte ==> " + Byte.MIN_VALUE + ", " + Byte.MAX_VALUE);
    }
}
