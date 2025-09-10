package com.example.utils;

import java.sql.*;
import java.util.Properties;

public class DBLogger {
    private final String url; // jdbc:mysql://host:port/
    private final String dbName;
    private final String user;
    private final String pass;

    public DBLogger(Properties props) {
        String host = props.getProperty("db.host");
        String port = props.getProperty("db.port");
        this.dbName = props.getProperty("db.name");
        this.user = props.getProperty("db.user");
        this.pass = props.getProperty("db.password");
        this.url = String.format("jdbc:mysql://%s:%s/", host, port);
        ensureDatabaseAndTable();
    }

    private Connection getConnection(String database) throws SQLException {
        String fullUrl = url + (database == null ? "" : database) + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        return DriverManager.getConnection(fullUrl, user, pass);
    }

    private void ensureDatabaseAndTable() {
        try (Connection conn = getConnection(null);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + dbName + "`");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create DB: " + e.getMessage(), e);
        }

        String createTable = "CREATE TABLE IF NOT EXISTS `" + dbName + "`.test_results ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "test_name VARCHAR(512),"
                + "status VARCHAR(50),"
                + "execution_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "duration_ms BIGINT,"
                + "error_message TEXT"
                + ") ENGINE=InnoDB";

        try (Connection c = getConnection(dbName);
             Statement s = c.createStatement()) {
            s.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table: " + e.getMessage(), e);
        }
    }

    public void insertResult(String testName, String status, long durationMs, String errorMessage) {
        String insert = "INSERT INTO `" + dbName + "`.test_results (test_name, status, duration_ms, error_message) VALUES (?,?,?,?)";
        try (Connection c = getConnection(dbName);
             PreparedStatement ps = c.prepareStatement(insert)) {
            ps.setString(1, testName);
            ps.setString(2, status);
            ps.setLong(3, durationMs);
            ps.setString(4, errorMessage);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to insert test result: " + e.getMessage());
        }
    }
}
