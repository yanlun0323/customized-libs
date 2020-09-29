package com.customized.libs.core.libs.command.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CurlTest {

    public static void main(String[] args) {
        String[] cmds = {"curl", "-H", "Host: www.chineseconverter.com", "-H", "Cache-Control: max-age=0", "--compressed",
                "https://www.chineseconverter.com/zh-cn/convert/chinese-stroke-order-tool"};

        String rt = execCurl(cmds);

        System.out.println(rt);
    }

    public static String execCurl(String[] cmds) {
        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return null;
    }
}
