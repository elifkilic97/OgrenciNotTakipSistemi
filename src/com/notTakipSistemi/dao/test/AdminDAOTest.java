package com.notTakipSistemi.dao.test;

import com.notTakipSistemi.dao.AdminDAO;
import com.notTakipSistemi.model.Admin;
import com.notTakipSistemi.dao.DBConnection;

import java.sql.Connection;

public class AdminDAOTest {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();

        if (connection == null) {
            System.out.println("Veritabanı bağlantısı başarısız!");
            return;
        }

        AdminDAO adminDAO = new AdminDAO(connection);

        // Yeni admin ekleme testi
        System.out.println("Yeni admin ekleniyor...");
        boolean created = adminDAO.createAdmin("Elif Admin", "elifadmin", "1234");
        if (created) {
            System.out.println("Admin başarıyla eklendi!");
        } else {
            System.out.println("Admin eklenemedi.");
        }

        // Admin bilgisi getirme testi
        System.out.println("\nAdmin bilgileri getiriliyor...");
        Admin admin = adminDAO.getAdminById(12); // Burada 1 yerine doğru admin id'sini ver
        if (admin != null) {
            System.out.println("Admin adı: " + admin.getName());
            System.out.println("Admin kullanıcı adı: " + admin.getUsername());
        } else {
            System.out.println("Admin bulunamadı.");
        }

        // Bağlantıyı kapat
        DBConnection.closeConnection(connection);
    }
}
