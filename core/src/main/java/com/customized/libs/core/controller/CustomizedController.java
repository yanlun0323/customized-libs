package com.customized.libs.core.controller;


import com.customized.libs.core.libs.lock.WaitInvoker;
import com.customized.libs.core.model.CommResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author yan
 */
@RestController
@RequestMapping("cst")
@Slf4j
public class CustomizedController extends BaseController {

    @Autowired
    private WaitInvoker waitInvoker;

    private static final ScheduledExecutorService EXECUTOR_SERVICE =
            ThreadUtils.newFixedThreadScheduledPool(50, "CustomizedThread", Boolean.TRUE);


    @RequestMapping(value = "act/001", method = RequestMethod.GET)
    public ResponseEntity<CommResp> act001(@RequestParam("msg") String msg) {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            EXECUTOR_SERVICE.schedule(() -> {
                try {
                    this.waitInvoker.
                            invoke(String.format("%s-%s", msg, finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 0, TimeUnit.MILLISECONDS);
        }
        return getMessageResp("success");
    }
}
