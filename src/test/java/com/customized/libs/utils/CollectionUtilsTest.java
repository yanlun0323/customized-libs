package com.customized.libs.utils;

import com.customized.libs.libs.entity.Keys;
import com.customized.libs.libs.entity.KeysTarget;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CollectionUtilsTest {

    @Test
    void translateCollection() {
        List<Keys> dt = new ArrayList<>();
        dt.add(Keys.builder().k("Key").v("Value").build());
        dt.add(Keys.builder().k("Key2").v("Value2").build());

        List<KeysTarget> target = CollectionUtils.translateCollection(dt, KeysTarget::new);
        System.out.println(target);
    }

    @Test
    void translateCollection1() {
        List<Keys> dt = new ArrayList<>();
        dt.add(Keys.builder().k("Key").v("Value").build());
        dt.add(Keys.builder().k("Key2").v("Value2").build());

        List<KeysTarget> target = CollectionUtils.translateCollection(dt, KeysTarget::new, (k, v) -> {
            System.out.println(k.getClass().getClassLoader());
            System.out.println(v.getClass().getClassLoader());
        });
        System.out.println(target);
    }
}