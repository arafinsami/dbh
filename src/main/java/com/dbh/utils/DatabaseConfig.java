package com.dbh.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.dbh.utils.AppConstant.*;

@Slf4j
public class DatabaseConfig {

    public static Connection getconnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            log.info("connected");
        } catch (Exception e) {
            log.info("Not connected");
        }
        return connection;
    }
}
