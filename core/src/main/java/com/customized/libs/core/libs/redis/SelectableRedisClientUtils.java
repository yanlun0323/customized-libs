package com.customized.libs.core.libs.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SelectableRedisClientUtils {

    private final Log logger = LogFactory.getLog(SelectableRedisClientUtils.class);
    private Pool<Jedis> pool;
    private boolean newVersion = false;

    public SelectableRedisClientUtils(int i) {
        init(i);
    }

    public Jedis getResource() {
        if (this.pool == null) {
            throw new RuntimeException("Redis Client not init!");
        }
        return (Jedis) this.pool.getResource();
    }

    public void closeResource(Jedis jedis) {
        this.pool.returnResource(jedis);
    }

    public void closeBrokenResource(Jedis jedis) {
        this.pool.returnBrokenResource(jedis);
    }

    private ResourceBundle resourceBundle = null;

    public void init(int databaseIndex) {
        try {
            Locale locale1 = new Locale("zh", "CN");
            this.resourceBundle = ResourceBundle.getBundle("config/redis-config", locale1);
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(toInteger("maxTotal"));
            config.setMaxIdle(toInteger("maxIdle"));
            config.setMaxWaitMillis(toInteger("maxWaitMillis"));
            config.setTestOnBorrow(toBoolean("testOnBorrow"));
            config.setTestOnReturn(toBoolean("testOnReturn"));
            String[] hostAndPorts = this.resourceBundle.getString("sentinels").trim().split(",");
            if (hostAndPorts.length > 1) {
                Set<String> sentinels = new HashSet(Arrays.asList(hostAndPorts));

                this.pool = new JedisSentinelPool(this.resourceBundle.getString("masterName").trim(), sentinels, config, toInteger("timeout"), this.resourceBundle.getString("password"), databaseIndex);
            } else {
                String[] hostAndPort = hostAndPorts[0].split(":");
                String host = hostAndPort[0];
                int port = Integer.parseInt(hostAndPort[1]);

                this.pool = new JedisPool(config, host, port, toInteger("timeout"), this.resourceBundle.getString("password"), databaseIndex, null);
            }
        } catch (Exception e) {
            this.logger.error("init redis pool fail", e);
            throw new RuntimeException("init redis pool fail", e);
        }
    }

    private int toInteger(String key) {
        return Integer.parseInt(this.resourceBundle.getString(key).trim());
    }

    private boolean toBoolean(String key) {
        return Boolean.valueOf(this.resourceBundle.getString(key).trim());
    }

    public boolean isNewVersion() {
        return this.newVersion;
    }

    public <T> T call(RedisCall<T> rc) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return (T) rc.run(jedis);
        } finally {
            if (jedis != null) {
                closeResource(jedis);
            }
        }
    }

    public static void main(String[] args) {
        SelectableRedisClientUtils util = new SelectableRedisClientUtils(9);
        String[] keys = {"308192036037", "305581003143", "103338764736", "305581003143", "301100000849", "102100099996", "104567016064", "102173401736", "102100099996", "102100099996", "103207041014", "307338702461", "102100000345"};
        for (String key : keys) {
            String value = util.call(jedis -> jedis.get(key));
            System.out.println(key + ":" + value);
        }
    }

    public static List<String> readXlsx(File file) {
        SelectableRedisClientUtils util = new SelectableRedisClientUtils(9);
        List<String> list = new ArrayList<>();

        InputStream input = null;
        XSSFWorkbook wb = null;
        String rowList = null;
        try {
            input = new FileInputStream(file);

            wb = new XSSFWorkbook(input);

            int numberOfSheets = wb.getNumberOfSheets();

            XSSFSheet xssfSheet = wb.getSheetAt(0);

            int totalRows = xssfSheet.getLastRowNum();
            for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    util.call(jedis -> {
                        jedis.set(xssfRow.getCell(1).getStringCellValue().trim(),
                                xssfRow.getCell(2).getStringCellValue().trim());
                        return null;
                    });
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
