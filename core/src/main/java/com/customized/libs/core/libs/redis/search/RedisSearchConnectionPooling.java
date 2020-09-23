package com.customized.libs.core.libs.redis.search;

import com.customized.libs.core.libs.redis.search.config.RedisSearchConst;
import com.redislabs.lettusearch.RediSearchClient;
import com.redislabs.lettusearch.RediSearchCommands;
import com.redislabs.lettusearch.StatefulRediSearchConnection;
import com.redislabs.lettusearch.search.SearchResults;
import io.lettuce.core.RedisURI;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 1、Create a RediSearch client
 * <p>
 * 2、Create a pool configuration
 * <p>
 * 3、Create the connection pool
 * <p>
 * 4、In worker threads, get connections in a try-with statement to automatically return them to the pool
 * <p>
 * 5、Use sync commands
 * <p>
 * 6、Execute commands
 */
@SuppressWarnings("all")
public class RedisSearchConnectionPooling {

    public static void main(String[] args) {
        RediSearchClient client = RediSearchClient.create(RedisURI.create(RedisSearchConst.HOST, RedisSearchConst.PORT)); // (1)
        GenericObjectPoolConfig config = new GenericObjectPoolConfig(); // (2)
        config.setMaxTotal(8);
        GenericObjectPool<StatefulRediSearchConnection<String, String>> pool = ConnectionPoolSupport
                .createGenericObjectPool(client::connect, config); // (3)

        // The connection pool can now be passed to worker threads
        try (StatefulRediSearchConnection<String, String> connection = pool.borrowObject()) { // (4)
            RediSearchCommands<String, String> commands = connection.sync(); // (5)
            SearchResults<String, String> results = commands.search("idx", "client*");// (6)
            results.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
