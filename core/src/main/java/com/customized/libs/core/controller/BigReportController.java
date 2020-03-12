package com.customized.libs.core.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.customized.libs.core.model.others.TerminalQueryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yan
 */
@RestController
@RequestMapping("big/report")
@Slf4j
public class BigReportController extends BaseController {

    @RequestMapping(value = "terminal/query", method = RequestMethod.GET)
    public ResponseEntity<List<TerminalQueryResp>> bigQuery(
            @RequestParam(value = "size", defaultValue = "200000") Integer size) {
        List<TerminalQueryResp> records = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TerminalQueryResp record = new TerminalQueryResp();
            record.setMfrNo("9");
            record.setOrgNm("湖南自然阳光食品有限工资");
            record.setMfrNm("自然阳光");
            record.setModNm("阳光1号");
            record.setTrmSn("7171000000000099");
            record.setMercName("自然阳光1号");
            record.setMercId("701000000000001");
            record.setBindStatus("003");
            record.setValidStatus("000");
            record.setActivityName("活动一号");

            records.add(record);
        }
        long begin = System.currentTimeMillis();
        // JSON.toJavaList
        String data = JSON.toJSONString(records);
        log.debug("Mock Data TimeConsuming ==> {} Data ==>> {}", System.currentTimeMillis() - begin, data);

        begin = System.currentTimeMillis();
        records = JSONArray.parseArray(data, TerminalQueryResp.class);
        log.debug("Mock Data Parse TimeConsuming ==> {}", System.currentTimeMillis() - begin);

        return getJsonResp(records, HttpStatus.OK);
    }
}
