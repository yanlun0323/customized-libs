package com.customized.libs.core.libs.javase.string;

import java.util.StringJoiner;

public class StringJoinerTest {

    public static void main(String[] args) {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add("user").add("age");
        System.out.println(joiner.toString());

        String join = String.join(",", "user", "age");
        System.out.println(join);
    }
}
