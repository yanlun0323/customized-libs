package com.example.spring.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/4/28 14:46
 */
@RestController
public class WebFluxUserController {

    @RequestMapping("/echo")
    public String echo(@RequestParam("echo") String echo) {
        long start = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("接口耗时：" + (System.currentTimeMillis() - start));
        return echo;
    }

    @GetMapping("/mono")
    public Mono<Map> getUser0() {
        long start = System.currentTimeMillis();
        Mono<Map> mapMono = Mono.fromSupplier(this::queryUser);
        System.out.println("接口耗时：" + (System.currentTimeMillis() - start));
        return mapMono;
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getUser1() {
        long start = System.currentTimeMillis();
        Flux<String> flux = Flux.fromArray(new String[]{"小黑", "小胖", "小六", "一鑫"}).map(s -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return String.format("姓名：%s", s);
        });
        System.out.println("接口耗时：" + (System.currentTimeMillis() - start));
        return flux;
    }

    private Map queryUser() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String, Object> user = new HashMap<>(2);
        user.put("name", "Yan");
        user.put("language", "JAVA");
        return user;
    }
}
