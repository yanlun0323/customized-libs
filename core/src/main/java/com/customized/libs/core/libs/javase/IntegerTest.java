package com.customized.libs.core.libs.javase;

public class IntegerTest {

    public static void main(String[] args) {
        String temp;
        String lastTN;
        for (int tradeAmt = 10; tradeAmt < 200000; tradeAmt++) {
            temp = String.valueOf(tradeAmt);
            // 获取最后两位数字
            lastTN = String.valueOf(tradeAmt).substring(temp.length() - 2);
            if (Long.parseLong(lastTN) % 11 == 0) {
                System.out.println(tradeAmt);
            }
        }
    }
}
