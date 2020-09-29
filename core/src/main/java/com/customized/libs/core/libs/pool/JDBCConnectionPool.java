package com.customized.libs.core.libs.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionPool extends ObjectPool<Connection> {

    private String driver, url, username, password;

    private JDBCConnectionPool() {

    }


    private static volatile JDBCConnectionPool instance;

    public static JDBCConnectionPool getInstance() {
        if (instance == null) {
            synchronized (JDBCConnectionPool.class) {
                if (instance == null) {
                    instance = new JDBCConnectionPool();
                }
            }
        }
        return instance;
    }

    @Override
    protected Connection create() {
        try {
            return (DriverManager.getConnection(url, username, password));
        } catch (SQLException e) {
            e.printStackTrace();
            return (null);
        }
    }

    @Override
    public void expire(Connection o) {
        try {
            ((Connection) o).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validate(Connection o) {
        try {
            return (!((Connection) o).isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            return (false);
        }
    }

    public String getDsn() {
        return url;
    }

    public void setDsn(String dsn) {
        this.url = dsn;
    }

    public String getUsr() {
        return username;
    }

    public void setUsr(String usr) {
        this.username = usr;
    }

    public String getPwd() {
        return password;
    }

    public void setPwd(String pwd) {
        this.password = pwd;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Deprecated
    public void loadDriver() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
        }
    }
}