package com.customized.libs.core.libs.javase.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yan
 */
public class CreateHashCodeSomeUtil {

    /***
     * 随机数种子，2个长度为2的hashCode一样的字符串
     */
    private static final String[] SEED = new String[]{"Aa", "BB"};

    public static List<String> build(int n) {
        List<String> init = Arrays.asList(SEED);
        for (int i = 1; i < n; i++) {
            init = creator(init);
        }
        return init;
    }

    public static List<String> creator(List<String> datasource) {
        List<String> result = new ArrayList<>();
        for (String s : SEED) {
            for (String str : datasource) {
                result.add(s.concat(str));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(build(1));
        System.out.println(build(2));
        System.out.println(build(3));
        System.out.println(build(4));
        System.out.println(build(5));
        System.out.println(build(6));
    }
}
