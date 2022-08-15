package com.customized.lib.web.service;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/7/22 16:33
 */
public class IpMergeDataSplit {

    @Test
    public void selectChinaIpRanges() throws IOException {
        File file = ResourceUtils.getFile(
                ResourceUtils.CLASSPATH_URL_PREFIX + "ip.merge.txt");
        List<String> ipRanges = FileUtils.readLines(file, "utf-8");

        // 归并分类
        Map<String, List<String>> merged = new HashMap<>(64);

        // 过滤其他地区的ipRanges
        List<String> targetIpRanges = ipRanges.parallelStream().filter(c -> {
            String[] segments = c.split("\\|");
            return (segments[2].contains("中国"));
        }).collect(Collectors.toList());

        List<String> filterIpRanges = targetIpRanges.parallelStream().filter(c -> {
            String[] segments = c.split("\\|");
            return (segments[2].contains("中国"));
        }).collect(Collectors.toList());

        filterIpRanges.forEach(c -> {
            String[] segments = c.split("\\|");
            List<String> temp = merged.getOrDefault(segments[5], new ArrayList<>(16));
            temp.add(c);
            merged.putIfAbsent(segments[5], temp);
        });

        System.out.println("Total Ip ==> " + filterIpRanges.size());
        filterIpRanges.forEach(System.out::println);

        for (Map.Entry<String, List<String>> groupEntry : merged.entrySet()) {
            System.out.println(groupEntry.getKey() + " ===>");
            System.out.println("Total Ip ==> " + groupEntry.getValue().size());
            groupEntry.getValue().forEach(System.out::println);
            System.out.println("\r\n");
        }
    }
}
