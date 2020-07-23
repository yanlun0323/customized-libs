package com.customized.libs.core.libs.bigdata;

import com.customized.libs.core.libs.bigdata.entity.KeyWords;
import com.customized.libs.core.utils.HashAlgorithms;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

/**
 * 1、分而治之，进行哈希取余；
 * 2、使用 HashMap 统计频数；
 * 3、求解最大的 TopN 个，用小顶堆；求解最小的 TopN 个，用大顶堆。
 * <p>
 * https://github.com/doocs/advanced-java/blob/master/docs/big-data/find-top-100-words.md
 *
 * @author yan
 */
public class FindTop100WordsV2 {

    /**
     * 忽略掉分割后的文件存储过程
     *
     * @param args
     */
    public static void main(String[] args) {
        doGetTopNWords(5000);
    }

    public static void doGetTopNWords(Integer elementSize) {
        String[] words = new String[elementSize];

        for (int i = 0; i < elementSize; i++) {
            words[i] = RandomStringUtils.randomNumeric(1, 10);
        }

        // 数据分组
        List<List<String>> items = splitWords(words, 500);

        // System.out.println(items);
        List<Map<String, Integer>> statistics = doStatistics(items);

        // System.out.println(statistics);

        List<KeyWords> topN = getTopN(statistics, 100);

        // System.out.println(topN);
    }

    /**
     * 从N个集合中整合topN，构造小顶堆
     * <p>
     * PriorityQueue用法
     * <p>
     * 技术应用：排序Map
     *
     * @param top
     * @return
     */
    public static List<KeyWords> getTopN(List<Map<String, Integer>> statistics, Integer top) {
        PriorityQueue<KeyWords> maxHeap = new PriorityQueue<>();
        for (int i = 0; i < statistics.size(); i++) {
            for (Map.Entry<String, Integer> entry : statistics.get(i).entrySet()) {
                maxHeap.add(new KeyWords(entry.getKey(), entry.getValue()));
                if (maxHeap.size() > top) {
                    maxHeap.poll();
                }
            }
        }

        return new ArrayList<>(maxHeap);
    }

    /**
     * StreamAPI compute方法
     *
     * @param items
     * @return
     */

    public static List<Map<String, Integer>> doStatistics(List<List<String>> items) {
        List<Map<String, Integer>> statistics = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Map<String, Integer> data = new HashMap<>(128);
            List<String> item = items.get(i);
            for (int j = 0; j < item.size(); j++) {
                data.compute(item.get(j), (k, v) -> {
                    v = v == null ? 1 : v + 1;
                    // System.out.println("K ==> " + k + "\t size ==> " + (v + 1));
                    return v;
                });
            }
            statistics.add(data);
        }
        return statistics;
    }


    /**
     * hash取余分配到不同的集合中
     *
     * @param words
     * @return
     */
    public static List<List<String>> splitWords(String[] words, Integer size) {
        List<List<String>> items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            items.add(new ArrayList<>());
        }

        for (int i = 0; i < words.length; i++) {
            int hash = HashAlgorithms.ELFHash(String.valueOf(words[i].hashCode())) % size;
            items.get(hash).add(words[i]);
        }

        return items;
    }
}
