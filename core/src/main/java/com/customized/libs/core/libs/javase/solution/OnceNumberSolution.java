package com.customized.libs.core.libs.javase.solution;

import com.alibaba.fastjson.JSON;

/**
 * 异或运算求解。
 * <p>
 * 首先明确，两个相同的数异或之后的结果为 0。对该数组所有元素进行异或运算，结果就是两个只出现一次的数字异或的结果。
 * <p>
 * 找出这个结果中某个二进制位为 1 的位置，
 * <p>
 * 之后对数组所有元素进行分类，二进制位为 0 的异或到 a，二进制位为 1 的异或到 b，结果就是 a，b。
 *
 * @author yan
 */
public class OnceNumberSolution {

    /**
     * 　(1)对于出现两次的元素，使用“异或”操作后结果肯定为0，那么我们就可以遍历一遍数组，对所有元素使用异或操作，那么得到的结果就是两个出现一次的元素的异或结果。
     * <p>
     * 　　(2)因为这两个元素不相等，所以异或的结果肯定不是0，也就是可以再异或的结果中找到1位不为0的位，例如异或结果的最后一位不为0。
     * <p>
     * 　　(3)这样我们就可以最后一位将原数组元素分为两组，一组该位全为1，另一组该位全为0。
     * <p>
     * 　　(4)再次遍历原数组，最后一位为0的一起异或，最后一位为1的一起异或，两组异或的结果分别对应着两个结果。
     *
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("8 toBinaryString ==> " + Integer.toBinaryString(8));


        int[] data = new int[]{1, 2, 10, 4, 1, 4, 3, 3};
        // int[] data = new int[]{4, 1, 4, 6};

        // rt == 两个只出现一次的数字异或的结果
        int xor = 0;
        for (int i = 0; i < data.length; i++) {
            xor ^= data[i];
        }


        System.out.println("xor-rt ==> " + xor);

        // 找出这个结果中某个二进制位为 1 的位置，利用>>位移操作
        int pos = 0;
        while ((xor & 1) == 0) {
            ++pos;
            xor >>= 1;
            System.out.println(Integer.toBinaryString(xor));
        }

        System.out.println("pos ==> " + pos);

        // 对数组所有元素进行分类，二进制位为 0 的异或到 a，二进制位为 1 的异或到 b
        int a = 0, b = 0;
        for (int num : data) {
            int t = num >> pos;
            System.out.println(String.format("[INFO] %s >> %s == %s",
                    Integer.toBinaryString(num), pos, Integer.toBinaryString(t)));

            if ((t & 1) == 0) {
                a ^= num;
            } else {
                b ^= num;
            }
        }


        System.out.println("[WARN] ==> " + JSON.toJSONString(new int[]{a, b}));
    }
}
