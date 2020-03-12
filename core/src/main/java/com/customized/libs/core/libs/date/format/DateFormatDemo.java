package com.customized.libs.core.libs.date.format;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateFormatDemo {

    public static void main(String[] args) {
        String now = DateTime.now().toString("YYYYMMdd");
        DateTime date0 = DateTime.parse("2019-12-31", DateTimeFormat.forPattern("YYYY-MM-dd"));

        System.out.println(date0);
        System.out.println(DateTime.parse("2019-12-31", DateTimeFormat.forPattern("yyyy-MM-dd")));

        System.out.println(DateFormatUtils.format(date0.toDate(), "YYYY-MM-dd"));
        System.out.println(DateFormatUtils.format(date0.toDate(), "yyyy-MM-dd"));
    }
}
