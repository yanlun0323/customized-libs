package com.customized.libs.core.libs.reactor;

import com.alibaba.fastjson.JSON;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

public class BufferFlux {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        //1.为了使用buffer功能，转换为Reactor的流对象Flux
        Flux flux = Flux.fromStream(IntStream.range(1, 10000).boxed());

        //2..聚合消费
        flux.buffer(20).subscribe(integers -> {
            System.out.println(JSON.toJSONString(integers));
        });

    }
}
