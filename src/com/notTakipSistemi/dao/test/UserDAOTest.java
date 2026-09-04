package com.notTakipSistemi.dao.test;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.UserDAO;
import com.notTakipSistemi.model.User;
import java.sql.Connection;

public class UserDAOTest {

    public static void main(String[] args) {
        // DBConnection sınıfını kullanarak bağlantıyı alıyoruz
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();

            // UserDAO nesnesi oluşturuluyor
            UserDAO userDAO = new UserDAO(connection);

            // Öğrenci ile giriş testi
            User student = userDAO.login("student1", "1234");
            if (student != null) {
                System.out.println("Başarıyla giriş yapıldı: " + student.getUsername());
            } else {
                System.out.println("Öğrenci girişi başarısız.");
            }

            // Öğretmen ile giriş testi
            User teacher = userDAO.login("teacher1", "1234");
            if (teacher != null) {
                System.out.println("Başarıyla giriş yapıldı: " + teacher.getUsername());
            } else {
                System.out.println("Öğretmen girişi başarısız.");
            }

            // Admin ile giriş testi
            User admin = userDAO.login("admin1", "1234");
            if (admin != null) {
                System.out.println("Başarıyla giriş yapıldı: " + admin.getUsername());
            } else {
                System.out.println("Admin girişi başarısız.");
            }

            // Yanlış şifre ile giriş testi
            User invalidUser = userDAO.login("student1", "wrongPassword");
            if (invalidUser == null) {
                System.out.println("Yanlış şifre girişi başarısız.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Bağlantıyı kapatıyoruz
            DBConnection.closeConnection(connection);
        }
    }
}
