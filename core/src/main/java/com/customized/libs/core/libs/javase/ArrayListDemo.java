package com.customized.libs.core.libs.javase;

import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {

    public static List<String> getData(int total) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            data.add("X");
        }
        return data;
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {
        List<String> temp = new ArrayList();
        //获取一批数据
        List<String> all = getData(100000000);
        for (String str : all) {
            temp.add(str);
        }
    }
}
