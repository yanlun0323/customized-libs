package com.customized.libs.core.libs.jdbc.driver;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class JDBCTest {

    public static void main(String[] args) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            System.out.println(drivers.nextElement().getClass() + "\t" + drivers.nextElement().getClass().getClassLoader());
        }
    }
}
