package com.customized.libs.core.libs.pool;

import cn.hutool.db.handler.BeanListHandler;
import com.alibaba.fastjson.JSON;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class JDBCPoolTest {

    public static void main(String[] args) throws SQLException {

        JDBCConnectionPool pool = JDBCConnectionPool.getInstance();

        // Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'.
        // The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
        pool.setDriver("com.mysql.jdbc.Driver");

        pool.setDsn("jdbc:mysql://172.19.100.113:3306/baseinfo?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");
        pool.setUsr("root");
        pool.setPwd("Qwer@1234");

        // pool.loadDriver();

        // Get a connection:
        Connection con = pool.checkOut();
        System.out.println("Conn@objId ==> " + con.toString());
        System.out.println(con.isValid(0));

        pool.checkIn(con);

        // query something
        con = pool.checkOut();
        System.out.println("Conn@objId ==> " + con.toString());

        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM t_bas_config LIMIT 0, 20");

        BeanListHandler<Map> beanHandler = new BeanListHandler<Map>(Map.class);
        List<Map> data = beanHandler.handle(rs);
        System.out.println(JSON.toJSONString(data, true));
        pool.checkIn(con);
    }
}