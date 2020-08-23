package com.customized.lib.web;

import com.blade.Blade;

/**
 * @author yan
 */
public class BladeWebStarter {

    public static void main(String[] args) {
        // idea占用了9000端口
        Blade.of().appName("Blade-Web").gzip(Boolean.TRUE)
                .listen(9800)
                .get("/", ctx -> ctx.text("Hello World!")).start(BladeWebStarter.class, args);
    }
}
