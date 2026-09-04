package com.notTakipSistemi.dao.test;

import com.notTakipSistemi.dao.DBConnection;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Bağlantı başarılı!");
            DBConnection.closeConnection(conn);
        } else {
            System.out.println("Bağlantı başarısız.");
        }
    }
}

