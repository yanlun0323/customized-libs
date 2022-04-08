package com.customized.libs.springboot.controller;

import cn.hutool.core.map.MapBuilder;
import com.jd.gobrs.async.gobrs.GobrsTaskFlow;
import com.jd.gobrs.async.result.AsyncResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/3/7 11:20
 */
@RestController
@Slf4j
public class GobrsTestController {

    @Resource
    private GobrsTaskFlow<Object> taskFlow;


    @GetMapping("gobrs/test")
    public ResponseEntity<AsyncResult> run() {
        MapBuilder<String, String> builder = MapBuilder.create();

        Map<String, Object> params = new HashMap<>(16);
        AsyncResult asyncResult = taskFlow.taskFlow("test", params, 100000L);

        return new ResponseEntity<>(asyncResult, HttpStatus.OK);
    }
}
