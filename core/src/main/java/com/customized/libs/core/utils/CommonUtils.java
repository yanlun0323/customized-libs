package com.customized.libs.core.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * @author yan
 */
public class CommonUtils {

    public static void main(String[] args) {
        for (long i = 0; i < 200L; i++) {
            System.out.println(fen2Yuan(i));
        }

        System.out.println(com.alibaba.dubbo.common.utils.StringUtils.camelToSplitName("HashAlgorithms", "_"));
        System.out.println(camelToSplitName("HashAlgorithms", "_"));
        System.out.println(camelToSplitName("hashAlgorithms", "_"));
        System.out.println(camelToSplitName("hash_algorithms", "_"));
        System.out.println(camelToSplitName("hashAlgorithmsTest", "_"));

        char ch = 'A' + 32;
        System.out.println(ch);
    }

    public static String buildLogString(String text, String delimiter, Integer length) {
        String delimiters = StringUtils.leftPad(delimiter, length, delimiter);
        return String.format("%s %s %s", delimiters, text, delimiters);
    }

    /**
     * BUG
     */
    @Deprecated
    public static String convert2Fee(long data) {
        return String.valueOf(data / 100);
    }


    /**
     * 将分转换为元(四舍五入到分)
     *
     * @param fen
     * @return
     */
    public static Double fen2Yuan(Long fen) {
        BigDecimal big = new BigDecimal(fen / 100.00);
        return big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String camelToSplitName(String str, String split) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                if (buf.length() == 0) {
                    buf.append((char) (ch + 32));
                } else {
                    buf.append(split).append((char) (ch + 32));
                }
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

}
