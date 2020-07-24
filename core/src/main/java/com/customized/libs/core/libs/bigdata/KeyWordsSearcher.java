package com.customized.libs.core.libs.bigdata;

import com.alibaba.fastjson.JSON;
import com.customized.libs.core.libs.bigdata.entity.KeyWords;
import com.customized.libs.core.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 根据关键字读取日志文件，按出现次数排序打印关键字
 * <p>
 * 初步实现方案，全部读取到内存，若大数据查找，则可采取分治思想，按行计算hash，分散到不同的日志中。再分别获取关键字累计，最后合计即可。
 *
 * @author yan
 */
public class KeyWordsSearcher {

    public static void main(String[] args) {

        PriorityQueue<KeyWords> keyWords = new PriorityQueue<>();
        keyWords.add(new KeyWords("1", 2));
        keyWords.add(new KeyWords("2", 99));
        keyWords.add(new KeyWords("3", 1));

        keyWords.add(new KeyWords("N/A", 2));


        keyWords.poll();
        System.out.println(keyWords);
        long currentTime = System.currentTimeMillis();

        List<KeyWords> keyWordsTotal = getKeyWordsTotal(Arrays.asList("INFO", "ERROR", "WARN", "DEBUG"),
                "/Users/yan/Workspace/workspace-card/customized-libs/logs/customized-libs.log");


        System.out.println(CommonUtils.buildLogString(String.format(" 查询耗时：%sms ",
                System.currentTimeMillis() - currentTime), "*", 32)
        );

        keyWordsTotal.forEach(keyWord -> {
            System.out.println(String.format("关键字：%s\t，出现次数：%s", keyWord.getKey(), keyWord.getTotal()));
        });

        System.out.println(CommonUtils.buildLogString(" 处理完成 ", "*", 32)
        );
    }

    public static List<KeyWords> getKeyWordsTotal(List<String> keywords, String filePath) {
        PriorityQueue<KeyWords> maxHeap = new PriorityQueue<>((int) Math.ceil(keywords.size() / 0.75) + 1);

        try {
            Map<String, Integer> data = new HashMap<>(
                    (int) Math.ceil(keywords.size() / 0.75) + 1);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            System.out.println(String.format("关键字：%s", JSON.toJSONString(keywords)));

            StringBuilder line;
            while ((line = new StringBuilder(bufferedReader.readLine())).length() != 0) {
                //由于split方法有一个特性，就是会忽略待分割对象结尾的一个或多个分割符，所以如果分割符（关键词）位于一行结尾，就无法进行统计
                //在此处我们给每行文本结尾添加一个自定义结束符"`"（注意，结束符不能干扰到关键词！）
                line.append("`");
                StringBuilder finalLine = line;

                keywords.forEach(keyword -> {
                    // 通过split方法实现搜索关键字次数功能
                    int count = (finalLine.toString().split(keyword)).length - 1;
                    Integer total = data.getOrDefault(keyword, 0) + count;
                    data.put(keyword, total);
                });
            }

            data.forEach((k, v) -> maxHeap.add(new KeyWords(k, v)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        KeyWords[] result = new KeyWords[maxHeap.size()];
        int index = maxHeap.size();
        for (int i = 0; i < keywords.size() && !maxHeap.isEmpty(); i++) {
            KeyWords data = maxHeap.poll();
            result[--index] = data;
        }

        return Arrays.asList(result);
    }
}
