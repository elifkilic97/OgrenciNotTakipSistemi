package com.notTakipSistemi.dao.test;


import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.StudentDAO;

import java.sql.Connection;


import com.notTakipSistemi.model.Student;

import java.sql.Connection;

public class StudentDAOTest {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Veritabanı bağlantısını aç
            connection = DBConnection.getConnection();

            if (connection == null) {
                System.out.println("Veritabanı bağlantısı kurulamadı.");
                return;
            }

            // StudentDAO nesnesini oluştur
            StudentDAO studentDAO = new StudentDAO(connection);

            // 1. Öğrenci oluştur
            System.out.println("Öğrenci oluşturuluyor...");
            Student newStudent = studentDAO.createStudent("Ali Yılmaz", "aliyilmaz", "password123");
            if (newStudent != null) {
                System.out.println("Oluşturulan öğrenci: " + newStudent);
            } else {
                System.out.println("Öğrenci oluşturulamadı.");
            }

            // 2. ID ile öğrenci bul
            if (newStudent != null) {
                System.out.println("\nID ile öğrenci bilgisi çekiliyor...");
                Student foundStudent = studentDAO.getStudentById(newStudent.getId());
                if (foundStudent != null) {
                    System.out.println("Bulunan öğrenci: " + foundStudent);
                } else {
                    System.out.println("Öğrenci bulunamadı.");
                }
            }

            // 3. Öğrenci bilgilerini güncelle
            if (newStudent != null) {
                System.out.println("\nÖğrenci bilgileri güncelleniyor...");
                boolean updated = studentDAO.updateStudent(newStudent.getId(), "Ali Veli Yılmaz", "ali_veli", "newpassword456");
                if (updated) {
                    System.out.println("Öğrenci başarıyla güncellendi.");
                } else {
                    System.out.println("Öğrenci güncellenemedi.");
                }
            }

            // 4. Kullanıcı adı ve şifre ile öğrenci bul
            System.out.println("\nKullanıcı adı ve şifre ile öğrenci aranıyor...");
            Student loginStudent = studentDAO.getStudentByUsernameAndPassword("ali_veli", "newpassword456");
            if (loginStudent != null) {
                System.out.println("Giriş yapan öğrenci: " + loginStudent);
            } else {
                System.out.println("Öğrenci bulunamadı veya şifre yanlış.");
            }

            // 5. Öğrenci silme
            if (newStudent != null) {
                System.out.println("\nÖğrenci siliniyor...");
                boolean deleted = studentDAO.deleteStudent(newStudent.getId());
                if (deleted) {
                    System.out.println("Öğrenci başarıyla silindi.");
                } else {
                    System.out.println("Öğrenci silinemedi.");
                }
            }

        } finally {
            // Veritabanı bağlantısını kapat
            DBConnection.closeConnection(connection);
        }
    }
}
