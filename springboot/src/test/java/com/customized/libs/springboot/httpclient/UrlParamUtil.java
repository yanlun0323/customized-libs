//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.customized.libs.springboot.httpclient;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UrlParamUtil {
    public UrlParamUtil() {
    }

    public static String createLinkString(Map<String, Object> params) {
        return createLinkString(params, false);
    }

    public static String createLinkString(Map<String, Object> params, Boolean encodeValue) {
        if (params != null && params.size() != 0) {
            List<String> keys = new ArrayList(params.keySet());
            Collections.sort(keys);
            StringBuffer linkString = new StringBuffer();

            for (int i = 0; i < keys.size(); ++i) {
                String key = (String) keys.get(i);
                Object value = params.get(key);
                if (value != null) {
                    if (encodeValue) {
                        try {
                            value = URLEncoder.encode(value.toString(), "UTF-8");
                        } catch (Throwable var8) {
                            continue;
                        }
                    }

                    if (linkString.length() > 0) {
                        linkString.append("&");
                    }

                    linkString.append(key).append("=").append(value);
                }
            }

            return linkString.toString();
        } else {
            return "";
        }
    }
}
