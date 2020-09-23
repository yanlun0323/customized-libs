package com.customized.libs.core.libs.redis.search;

import com.customized.libs.core.libs.redis.search.config.RedisSearchConst;
import com.redislabs.lettusearch.RediSearchAsyncCommands;
import com.redislabs.lettusearch.RediSearchClient;
import com.redislabs.lettusearch.StatefulRediSearchConnection;
import com.redislabs.lettusearch.search.Document;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 1、Create a RediSearch client
 * <p>
 * 2、Connect to RediSearch
 * <p>
 * 3、Use async commands
 * <p>
 * 4、Disable command auto-flush
 * <p>
 * 5、Call commands to be executed in a pipeline
 * <p>
 * 6、Add command execution future to the list
 * <p>
 * 7、Flush commands
 * <p>
 * 8、Wait for response from each future
 */
@SuppressWarnings("all")
@Slf4j
public class RedisSearchPipelining {

    public static void main(String[] args) {
        RediSearchClient client = RediSearchClient.create(RedisURI.create(RedisSearchConst.HOST, RedisSearchConst.PORT)); // (1)
        StatefulRediSearchConnection<String, String> connection = client.connect(); // (2)
        RediSearchAsyncCommands<String, String> commands = connection.async(); // (3)
        commands.setAutoFlushCommands(false); // (4)
        List<RedisFuture<?>> futures = new ArrayList<>();

        List<Document<String, String>> docs = new ArrayList<>();
        Document<String, String> doc0 = new Document<>();
        doc0.put("Y", "Java");
        doc0.put("T", "Java");
        doc0.setPayload("Redis Search");
        doc0.setScore(1.0);
        doc0.setSortKey("Redis");
        doc0.setId(RandomStringUtils.randomAlphabetic(10));
        docs.add(doc0);

        for (Document<String, String> doc : docs) { // (5)
            RedisFuture<?> future = commands.add("idx", doc); // (6)
            futures.add(future);
        }
        commands.flushCommands(); // (7)
        for (RedisFuture<?> future : futures) {
            try {
                Object dt = future.get(1, TimeUnit.SECONDS); // (8)
                System.out.println(dt);
            } catch (InterruptedException e) {
                log.debug("Command interrupted", e);
            } catch (ExecutionException e) {
                log.error("Could not execute command", e);
            } catch (TimeoutException e) {
                log.error("Command timed out", e);
            }
        }
    }
}
