package com.customized.libs.core.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * @author yan
 */
public class CommonUtils {

    public static void main(String[] args) {
        for (long i = 0; i < 2000000L; i++) {
            System.out.println(fen2Yuan(i));
        }
    }

    public static String buildLogString(String text, String delimiter, Integer length) {
        String delimiters = StringUtils.leftPad(delimiter, length, delimiter);
        return String.format("%s%s%s", delimiters, text, delimiters);
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
}
