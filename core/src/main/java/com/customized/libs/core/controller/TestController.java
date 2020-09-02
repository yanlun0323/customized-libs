package com.customized.libs.core.controller;

import com.customized.multiple.versions.adapter.annotations.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @PostMapping
    public Object test(@RequestBody Map requestBody) {
        LOGGER.info("requestBody={}", requestBody);
        return requestBody;
    }

    @Version("version>=5")
    @RequestMapping(value = "bingo", method = RequestMethod.GET)
    public Object testV1() {
        return "v1";
    }

    @Version("version<5")
    @RequestMapping(value = "bingo", method = RequestMethod.GET)
    public Object testV2() {
        return "v2";
    }
}