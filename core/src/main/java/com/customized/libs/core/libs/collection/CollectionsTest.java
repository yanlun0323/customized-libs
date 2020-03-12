package com.customized.libs.core.libs.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yan
 */
public class CollectionsTest {

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("A");
        data.add("B");

        String[] items = data.toArray(new String[0]);
        System.out.println(items.length);
    }
}
