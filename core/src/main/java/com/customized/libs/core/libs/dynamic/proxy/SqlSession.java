package com.customized.libs.core.libs.dynamic.proxy;

public class SqlSession {

    public String executeSQL(String sql) {
        System.out.println(sql);
        return sql + " ==> executed!!";
    }

}
