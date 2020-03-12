package com.customized.libs.core.utils;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouyang
 */
public class HttpUtils {

    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        @SuppressWarnings("rawtypes")
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String paramName = (String) names.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
        }
        return params;
    }

    /**
     * 获取发起Http请求的原始IP地址
     *
     * @param request HttpServletRequest
     * @return ip String
     * @throws Exception
     */
    public static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getInputStreamAsString(InputStream inputStream)
            throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder buf = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buf.append(line);
        }
        return buf.toString();
    }

    public static String getParameter(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        return (null == param) ? null : param.trim();
    }
}