package com.customized.libs.core.libs.date.format;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatDemo {

    public static void main(String[] args) throws ParseException {
        String now = DateTime.now().toString("YYYYMMdd");
        DateTime date0 = DateTime.parse("2019-12-31", DateTimeFormat.forPattern("YYYY-MM-dd"));

        System.out.println(date0);
        System.out.println(DateTime.parse("2019-12-31", DateTimeFormat.forPattern("yyyy-MM-dd")));

        System.out.println(DateFormatUtils.format(date0.toDate(), "YYYY-MM-dd"));
        System.out.println(DateFormatUtils.format(date0.toDate(), "yyyy-MM-dd"));

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
        Date date = formatter2.parse("20200731");

        System.out.println("==> Alipay/WeixinPay Time Range");

        System.out.println(getStartTime(date));
        System.out.println(getEndTime(date));

        System.out.println("==> Unionpay Time Range");
        System.out.println(getUnionpayStartTime(date));
        System.out.println(getUnionpayEndTime(date));
    }


    private static String getStartTime(Date date) {
        long curMillisecond = date.getTime();
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format2.format(new Date(curMillisecond));
    }

    private static String getEndTime(Date date) {
        int dayMis = 86400000;
        long curMillisecond = date.getTime();
        long resultMis = curMillisecond + (long) (dayMis - 1);
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultDate = new Date(resultMis);
        return format2.format(resultDate);
    }

    private static String getUnionpayEndTime(Date date) {
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        return format2.format(date) + " 22:59:59";
    }

    private static String getUnionpayStartTime(Date date) {
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        String reStr = "";

        try {
            Date dt = format2.parse(format2.format(date));
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(6, -1);
            Date dt1 = rightNow.getTime();
            reStr = format2.format(dt1) + " 23:00:00";
        } catch (ParseException var6) {
            var6.printStackTrace();
        }

        return reStr;
    }

}
