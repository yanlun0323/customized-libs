package com.customized.libs.shardingsphere.controller;

import com.customized.libs.shardingsphere.controller.common.BaseController;
import com.customized.libs.shardingsphere.entity.common.CommResp;
import com.customized.libs.shardingsphere.entity.common.SDKConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class ShardingController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ShardingController.class);

    @ResponseBody
    @GetMapping(value = "/sharding/ping")
    public ResponseEntity<Object> ping() {
        CommResp commResp = new CommResp();
        commResp.setData("Pong");
        commResp.setKey(SDKConst.CommonRespCode.OK);
        return this.getJsonResp(commResp);
    }

    @ResponseBody
    @GetMapping(value = "httpclient/multi1")
    public ResponseEntity<String> invoke1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return this.getStringResp("OK");
    }

    @ResponseBody
    @GetMapping(value = "httpclient/multi2")
    public ResponseEntity<String> invoke2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return this.getStringResp("OK");
    }
}
