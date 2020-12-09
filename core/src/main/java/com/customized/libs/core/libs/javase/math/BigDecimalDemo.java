package com.customized.libs.core.libs.javase.math;

import java.math.BigDecimal;

/**
 * @author yan
 */
public class BigDecimalDemo {

    /**
     * BigDecimal数值比对时应该用compareTo比对，弃用equals，
     * <p>
     * 因为equals比对会比较精度，导致比对结果与预期不符
     *
     * @param args
     */
    public static void main(String[] args) {
        BigDecimal one = BigDecimal.ONE;
        BigDecimal one1 = new BigDecimal("1.0");
        System.out.println(one.equals(one1));
        System.out.println(one.compareTo(one1) == 0);
    }
}
