package com.customized.libs.core.libs.reactor.io;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

public class Client {

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        final Server[] server = {null};
        new Thread(() -> {
            server[0] = new Server(9988);
            server[0].start();
        }).start();

        TimeUnit.SECONDS.sleep(0b101);
        Acceptor acceptor = server[0].getAcceptor();
        acceptor.addNewConnection(new InputSource("ABC", Long.parseLong(DateTime.now().toString("yyyyMMddHHmmss"))));
    }
}
