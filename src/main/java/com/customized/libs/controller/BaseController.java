package com.customized.libs.controller;

import com.customized.libs.config.SDKConst;
import com.customized.libs.model.CommResp;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BaseController {

    public final static String DEFAULT_LIST_PAGE_INDEX = "0";
    public final static String DEFAULT_LIST_PAGE_SIZE = "20";

    public <T> ResponseEntity<T> getJsonResp(T body, HttpStatus statusCode) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<>(body, responseHeaders, statusCode);
    }

    public ResponseEntity<CommResp> getMessageResp(String message) {
        CommResp translate = new CommResp();
        translate.setKey(SDKConst.CommonRespCode.OK);
        translate.setMsg(message);
        return getJsonResp(translate, HttpStatus.OK);
    }

    public ResponseEntity<String> getStringResp(String message) {
        return getJsonResp(message, HttpStatus.OK);
    }

    public Integer translatePage(Integer startRecord, Integer maxRecords) {
        return (startRecord / maxRecords) + 1;//page从1开始
    }

    public Map<String, Object> translateStandardBizParams(HttpServletRequest request) {
        Map<String, Object> bizParams = new HashMap<>(10);
        Map<String, String> req = new HashMap<>(20);
        Enumeration<?> queryNames = request.getParameterNames();
        while (queryNames.hasMoreElements()) {
            String name = (String) queryNames.nextElement();
            if ("page".equals(name) || "pageSize".equals(name)) {
                bizParams.put(name, request.getParameter(name));
            } else {
                req.put(name, request.getParameter(name));
            }
        }
        bizParams.put("req", req);
        this.setDefaultParamsIfNecessary(bizParams);
        return bizParams;
    }

    public Map<String, Object> translateSimpleBizParams(HttpServletRequest request) {
        Map<String, Object> bizParams = new HashMap<>();
        Enumeration<?> queryNames = request.getParameterNames();
        while (queryNames.hasMoreElements()) {
            String name = (String) queryNames.nextElement();
            bizParams.put(name, request.getParameter(name));
        }
        this.setDefaultParamsIfNecessary(bizParams);
        return bizParams;
    }

    private void setDefaultParamsIfNecessary(Map<String, Object> bizParams) {
        if (!bizParams.containsKey("page")) bizParams.put("page", Integer.valueOf(DEFAULT_LIST_PAGE_INDEX));
        if (!bizParams.containsKey("pageSize")) bizParams.put("pageSize", Integer.valueOf(DEFAULT_LIST_PAGE_SIZE));
    }
}
