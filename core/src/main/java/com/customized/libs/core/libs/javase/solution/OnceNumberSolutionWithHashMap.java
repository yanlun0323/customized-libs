package com.customized.libs.core.libs.javase.solution;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class OnceNumberSolutionWithHashMap {

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

        int[] data = new int[]{1, 2, 10, 4, 1, 4, 3, 3};
        // int[] data = new int[]{4, 1, 4, 6};

        Map<Integer, Integer> container = new HashMap<>(64);
        for (int aData : data) {
            container.compute(aData, (k, v) -> container.containsKey(k) ? container.get(k) + 1 : 1);
        }

        System.out.println(container);

        List<Integer> rt = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : container.entrySet()) {
            if (entry.getValue() == 1) {
                rt.add(entry.getKey());
            }
        }

        System.out.println(rt);

        System.out.println("[INFO] 方案二：StreamAPI");

        System.out.println(container
                .entrySet()
                .stream()
                .filter((entry) -> (entry.getValue() == 1))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
        );

        Map grouping = Stream.of(1, 2, 10, 4, 1, 4, 3, 3).collect(Collectors.groupingBy(Function.identity()));
        System.out.println("GroupingBy ==> Function.identity() ==> " + JSON.toJSONString(grouping));


        // Function.identity() ==> t == t
        Map<Integer, Long> counting = Stream.of(1, 2, 10, 4, 1, 4, 3, 3).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("GroupingBy Counting ==> Function.identity() ==> " + JSON.toJSONString(counting));

        System.out.println("[INFO] 方案三：StreamAPI GroupingBy");
        System.out.println(counting.entrySet()
                .stream()
                .filter((entry) -> (entry.getValue() == 1))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()));

        System.out.println("[WARN]");
        System.out.println("[WARN]");

        // StreamAPI demo
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");
        // 分组
        Map<String, List<String>> result1 = items.stream().collect(
                Collectors.groupingBy(
                        Function.identity()
                )
        );
        //{papaya=[papaya], orange=[orange], banana=[banana, banana], apple=[apple, apple, apple]}
        System.out.println(result1);
        // 分组计数
        Map<String, Long> result2 = items.stream().collect(
                Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                )
        );
        // {papaya=1, orange=1, banana=2, apple=3}
        System.out.println(result2);
    }
}
