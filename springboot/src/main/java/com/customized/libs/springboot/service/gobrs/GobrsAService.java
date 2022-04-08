package com.customized.libs.springboot.service.gobrs;

import cn.hutool.core.util.RandomUtil;
import com.jd.gobrs.async.gobrs.GobrsAsyncSupport;
import com.jd.gobrs.async.task.AsyncTask;
import com.jd.gobrs.async.task.TaskResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/3/7 11:30
 */
@Service("GobrsAService")
@Slf4j
public class GobrsAService implements AsyncTask<Map<String, String>, Void> {


    @SneakyThrows
    @Override
    public Void task(Map<String, String> params, GobrsAsyncSupport support) {
        TimeUnit.MILLISECONDS.sleep(RandomUtil.randomInt(1000, 2000));
        params.put("A", "GobrsAService");
        log.info("Gobrs A Service Execute! ==> {}", params);
        return null;
    }

    @Override
    public boolean nessary(Map<String, String> params, GobrsAsyncSupport support) {
        return true;
    }

    @Override
    public void callback(boolean success, Map<String, String> param, TaskResult<Void> workResult) {
        log.info("==> Work Result ==> {}", workResult.getResultState());
    }
}
