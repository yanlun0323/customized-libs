package com.customized.libs.core.libs.bloom.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;


/**
 * BloomFilterTest
 *
 * @author sage.wang
 * @date 18-5-14 下午5:02
 */
public class BloomFilterTest2 {

    public static void main(String[] args) {
        int expectedInsertions = 10000000;
        double fpp = 0.00001;

        BloomFilter<Integer> state = BloomFilter.create(Funnels.integerFunnel(), expectedInsertions, fpp);
        state.put(1);
        state.put(2);
        state.put(3);
        System.out.println(state.mightContain(1));
        System.out.println(state.mightContain(2));
        System.out.println(state.mightContain(3));
    }
} 