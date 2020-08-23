package com.customized.lib.web.service.baseloc.region; /**
 * 国内单基站查询接口:http://api.cellocation.com:81/cell/
 *
 * @version 1.00
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CellocationAPI {

    public static String mnc = "1";                                //mnc
    public static String lac = "4301";                             //lac
    public static String cellid = "20986";                            //cell

    public static String getWebData(String domain) {
        StringBuffer sb = new StringBuffer();
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
        try {
            java.net.URL url = new java.net.URL(domain);
            is = url.openStream();
            isr = new InputStreamReader(is, "utf-8");
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
                if (isr != null) {
                    isr.close();
                    isr = null;
                }
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String data = getWebData("http://api.cellocation.com:81/cell/?mcc=460&mnc=" + mnc + "&lac=" + lac + "&ci=" + cellid + "&output=csv");

        String errcode = "";
        String lat = "";
        String lon = "";
        String radius = "";
        String address = "";
        if (data != "") {
            String[] arr = data.split(",");
            errcode = arr[0];
            if (errcode.equals("0")) {
                lat = arr[1];
                lon = arr[2];
                radius = arr[3];
                address = arr[4];
            } else if (errcode.equals("10000")) {
                System.out.println("参数错误");
            } else if (errcode.equals("10001")) {
                System.out.println("无查询结果");
            }
        }

        System.out.println("状态：" + errcode);
        System.out.println("经度：" + lon);
        System.out.println("纬度：" + lat);
        System.out.println("范围半径：" + radius);
        System.out.println("地址：" + address);
    }
}