package data;

import data.interfaces.IDB;

import java.sql.*;

public class PostgresDB implements IDB {
    private static PostgresDB instance;
    private String host;
    private String username;
    private String password;
    private String dbName;

    private Connection connection;

    public PostgresDB(String host, String username, String password, String dbName) {
        setHost(host);
        setUsername(username);
        setPassword(password);
        setDbName(dbName);
    }
    public static synchronized PostgresDB getInstance(String host, String username, String password, String dbName) {
        if (instance == null) {
            instance = new PostgresDB(host, username, password, dbName);
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        String connectionUrl = host + "/" + dbName;
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            // Here we load the driver’s class file into memory at the runtime
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(connectionUrl, username, password);

            return connection;
        } catch (Exception e) {
            System.out.println("failed to connect to postgres: " + e.getMessage());

            return null;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Connection close error: " + ex.getMessage());
            }
        }
    }
}
