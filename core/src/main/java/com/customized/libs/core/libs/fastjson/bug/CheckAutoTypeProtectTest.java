package com.customized.libs.core.libs.fastjson.bug;

import com.alibaba.fastjson.JSONException;

/**
 * https://www.hollischuang.com/archives/5177
 * checkAutoType bug fixed by fastJSON
 *
 * @author yan
 */
public class CheckAutoTypeProtectTest {

    public static void main(String[] args) {
        // checkAutoType("LLcom.sun.rowset.JdbcRowSetImpl;;");
        checkAutoType("[com.sun.rowset.JdbcRowSetImpl");
    }

    public static void checkAutoType(String typeName) {
        String className = typeName.replace('$', '.');

        final long BASIC = 0xcbf29ce484222325L;
        final long PRIME = 0x100000001b3L;

        final long h1 = (BASIC ^ className.charAt(0)) * PRIME;
        // 计算以"["开头的typeName，抛出异常
        if (h1 == 0xaf64164c86024f1aL) { // [
            throw new JSONException("autoType is not support. " + typeName);
        }

        // 计算以";"开头的typeName，抛出异常
        if ((h1 ^ className.charAt(className.length() - 1)) * PRIME == 0x9198507b5af98f0L) {
            throw new JSONException("autoType is not support. " + typeName);
        }
    }
}
