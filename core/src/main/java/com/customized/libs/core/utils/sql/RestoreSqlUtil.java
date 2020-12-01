package com.customized.libs.core.utils.sql;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * restore the mybatis generate sql to original whole sql
 *
 * @author ob
 */
public class RestoreSqlUtil {

    private static final Set<String> NEED_ASSEMBLED_TYPE = new HashSet<>();
    private static final String QUESTION_MARK = "?";
    private static final String REPLACE_MARK = "_o_?_b_";
    private static final String PARAM_TYPE_REGEX = "\\(\\D{3,30}?\\),?";

    static {
        NEED_ASSEMBLED_TYPE.add("(String)");
        NEED_ASSEMBLED_TYPE.add("(Timestamp)");
        NEED_ASSEMBLED_TYPE.add("(Date)");
        NEED_ASSEMBLED_TYPE.add("(Time)");
    }

    public static String match(String p, String str) {
        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    public static String restoreSql(final String preparing, final String parameters) {
        String restoreSql = "";
        String preparingSql = "";
        String parametersSql = "";
        try {
            if (preparing.contains(StringConst.PREPARING)) {
                preparingSql = preparing.split(StringConst.PREPARING)[1].trim();
            } else {
                preparingSql = preparing;
            }
            boolean hasParam = false;
            if (parameters.contains(StringConst.PARAMETERS)) {
                if (parameters.split(StringConst.PARAMETERS).length > 1) {
                    parametersSql = parameters.split(StringConst.PARAMETERS)[1];
                    if (StringUtils.isNotBlank(parametersSql)) {
                        hasParam = true;
                    }
                }
            } else {
                parametersSql = parameters;
            }
            if (hasParam) {
                preparingSql = StringUtils.replace(preparingSql, QUESTION_MARK, REPLACE_MARK);
                preparingSql = StringUtils.removeEnd(preparingSql, "\n");
                parametersSql = StringUtils.removeEnd(parametersSql, "\n");
                int questionMarkCount = StringUtils.countMatches(preparingSql, REPLACE_MARK);
                // 这里比较重要，通过正则表达式分割出所有的sql参数
                String[] paramArray = parametersSql.split(PARAM_TYPE_REGEX);
                for (int i = 0; i < paramArray.length; ++i) {
                    if (questionMarkCount <= paramArray.length || !parametersSql.contains("null")) {
                        break;
                    } else {
                        parametersSql = parametersSql.replaceFirst("null", "null(Null)");
                    }
                    paramArray = parametersSql.split(PARAM_TYPE_REGEX);
                }
                for (int i = 0; i < paramArray.length; ++i) {
                    paramArray[i] = StringUtils.removeStart(paramArray[i], " ");
                    parametersSql = StringUtils.replaceOnce(StringUtils.removeStart(parametersSql, " "), paramArray[i], "");
                    String paramType = match("(\\(\\D{3,25}?\\))", parametersSql);
                    preparingSql = StringUtils.replaceOnce(preparingSql, REPLACE_MARK, assembledParamValue(paramArray[i], paramType));
                    paramType = paramType.replace("(", "\\(").replace(")", "\\)") + ", ";
                    parametersSql = parametersSql.replaceFirst(paramType, "");
                }
            }
            restoreSql = simpleFormat(preparingSql);
            if (!restoreSql.endsWith(";")) {
                restoreSql += ";";
            }
        } catch (Exception e) {
            return "restore mybatis sql error!";
        }
        return restoreSql;
    }

    public static String assembledParamValue(String paramValue, String paramType) {
        if (NEED_ASSEMBLED_TYPE.contains(paramType)) {
            paramValue = "'" + paramValue + "'";
        }
        return paramValue;
    }

    public static String simpleFormat(String sql) {
        if (StringUtils.isNotBlank(sql)) {
            return sql.replaceAll("select", "SELECT")
                    .replaceAll("delete", "DELETE")
                    .replaceAll("update", "UPDATE")
                    .replaceAll("insert", "INSERT")
                    .replaceAll("(?i)\\s+from\\s+", "\n FROM ")
                    .replaceAll("(?i)\\s+where\\s+", "\n WHERE ")
                    .replaceAll("(?i)\\s+left join\\s+", "\n LEFT JOIN ")
                    .replaceAll("(?i)\\s+right join\\s+", "\n RIGHT JOIN ")
                    .replaceAll("(?i)\\s+inner join\\s+", "\n INNER JOIN ")
                    .replaceAll("(?i)\\s+limit\\s+", "\n LIMIT ")
                    .replaceAll("(?i)\\s+on\\s+", "\n ON ")
                    .replaceAll("(?i)\\s+union\\s+", "\n UNION ");
        }
        return "";
    }
}