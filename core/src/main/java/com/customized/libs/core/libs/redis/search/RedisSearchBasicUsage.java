package com.customized.libs.core.libs.redis.search;

import com.customized.libs.core.libs.redis.search.config.RedisSearchConst;
import com.redislabs.lettusearch.RediSearchClient;
import com.redislabs.lettusearch.RediSearchCommands;
import com.redislabs.lettusearch.StatefulRediSearchConnection;
import com.redislabs.lettusearch.index.Schema;
import com.redislabs.lettusearch.index.field.TextField;
import com.redislabs.lettusearch.search.Document;
import com.redislabs.lettusearch.search.SearchResults;
import io.lettuce.core.RedisURI;

/**
 * 1、Create a RediSearch client
 * <p>
 * 2、Connect to RediSearch
 * <p>
 * 3、Use sync, async, or reactive commands
 * <p>
 * 4、Create an index
 * <p>
 * 5、Add a document to the index
 * <p>
 * 6、Search the index
 */
@SuppressWarnings("all")
public class RedisSearchBasicUsage {

    private static final String NAME = "KEY0";
    private static final String ID = "RS:ID:3.0";

    public static void main(String[] args) {
        RediSearchClient client = RediSearchClient.create(RedisURI.create(RedisSearchConst.HOST, RedisSearchConst.PORT)); // (1)

        StatefulRediSearchConnection<String, String> connection = client.connect(); // (2)

        RediSearchCommands<String, String> commands = connection.sync(); // (3)

        // 此处创建Index的name()必须与添加到index的Document内部的name保持一致，才能搜索出来。
        commands.create("idx1.0", Schema.<String>builder().field(TextField.<String>builder().name(NAME).build()).build()); // (4)

        commands.add("idx1.0", Document.<String, String>builder().id(ID).score(1D)
                .field("KEY0", "Redis Basic Usage").field("KEY1", "Redis Pipelining").build()
        ); // (5)

        SearchResults<String, String> results = commands.search("idx1.0", "basic*"); // (6)

        results.forEach(System.out::println);
    }
}
