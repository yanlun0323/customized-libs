package com.customized.libs.core.libs.collection;

import com.customized.libs.core.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection去重复的方式：
 * <p>
 * 1、通过LinkedHashSet：去重后能保证顺序
 * <p>
 * 2、通过HashSet：去重后不能保证顺序
 * <p>
 * 3、HashMap：内部通过LinkedHashMap可保证顺序，实际上1、2均依赖于Map
 *
 * @author yan
 */
public class CollectionsTest {

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("A");
        data.add("B");

        String[] items = data.toArray(new String[0]);
        System.out.println(items.length);

        List<String> strings = new ArrayList<>();
        strings.add("Java");
        strings.add("Java");
        strings.add("Java");
        strings.add("C#");
        strings.add("C++");
        strings.add("C");
        strings.add("python");
        strings.add("lua");
        strings.add("kotlin");
        strings.add("Object C");
        strings.add("Swift");
        strings.add("Javascript");
        strings.add("Go");
        System.out.println("LinkedHashSet ==> " + CollectionUtils.removeDuplicates(strings));
        System.out.println("HashSet ==> " + CollectionUtils.removeDuplicatesWithHashSet(strings));
        System.out.println("HashMap ==> " + CollectionUtils.removeDuplicatesWithHashMap(strings));
    }
}
