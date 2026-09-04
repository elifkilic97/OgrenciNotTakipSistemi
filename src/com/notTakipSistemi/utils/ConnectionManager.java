package com.notTakipSistemi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private static Connection connection;

    private ConnectionManager() {
        // private constructor: bu sınıftan nesne oluşturulmasın
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Veritabanına bağlanırken hata oluştu: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // kapandıktan sonra sıfırla
            } catch (SQLException e) {
                System.out.println("Bağlantı kapatılırken hata oluştu: " + e.getMessage());
            }
        }
    }
}
