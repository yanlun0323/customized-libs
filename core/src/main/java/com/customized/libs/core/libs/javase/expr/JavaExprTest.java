package com.customized.libs.core.libs.javase.expr;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 */
public class JavaExprTest {

    public static void main(String[] args) {
        Map<String, Boolean> map = new HashMap<>(16);
        Boolean b = (map != null ? map.get("test") : Boolean.FALSE);
        Boolean b2 = (map != null ? map.get("test") : false);
        System.out.println(b);
        System.out.println(b2);

    }
}
