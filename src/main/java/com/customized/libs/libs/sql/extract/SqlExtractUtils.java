package com.customized.libs.libs.sql.extract;


import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlExtractUtils {

    public static final Map<String, String> KEYWORDS_FOLLOW_KEYWORD = new HashMap<>();

    static {
        KEYWORDS_FOLLOW_KEYWORD.put("from", "WHERE 0&@ 0");
        // ,分割后取0，=分割后取1
        KEYWORDS_FOLLOW_KEYWORD.put("partition", ", 0&= 1");

        KEYWORDS_FOLLOW_KEYWORD.put("join", "@ 0");
    }

    /**
     * Sql关键字抽取（忽略大小写）
     *
     * @param sql
     * @param keywords
     * @return
     */
    public static List<String> extract(String sql, String[] keywords) {
        sql = sql.toUpperCase();
        List<String> data = new ArrayList<>(16);
        String tempStr;
        for (int i = 0; i < keywords.length; i++) {
            String[] temp = StringUtils.
                    delimitedListToStringArray(sql, keywords[i].toUpperCase());
            SqlExtractUtils.sqlSpecialCharFilters(keywords[i], data, temp);
        }
        return data;
    }

    private static void sqlSpecialCharFilters(String keyword, List<String> data, String[] sql) {
        String tempStr;// 去除特殊字符及回车符
        for (int j = 0; j < sql.length; j++) {
            tempStr = sql[j].toUpperCase().replace("\n", "")
                    .replace("(", "").trim();
            if (!tempStr.startsWith("SELECT") && !tempStr.startsWith("INSERT")) {
                if (KEYWORDS_FOLLOW_KEYWORD.containsKey(keyword)) {
                    String[] delimiter = KEYWORDS_FOLLOW_KEYWORD.get(keyword).split("\\&");
                    for (int i = 0; i < delimiter.length; i++) {
                        String[] splited = delimiter[i].split(" ");
                        tempStr = tempStr.split(splited[0].replace("@", " "))[Integer.parseInt(splited[1])];
                    }
                }
                data.add(tempStr.trim().replaceAll("'", ""));
            }
        }
    }
}
