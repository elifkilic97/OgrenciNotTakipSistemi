package com.notTakipSistemi.dao.test;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.TeacherDAO;
import com.notTakipSistemi.model.Teacher;

import java.sql.Connection;

public class TeacherDAOTest {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
        TeacherDAO teacherDAO = new TeacherDAO(connection);

        // Yeni öğretmen ekleme testi
        System.out.println("Yeni öğretmen ekleniyor...");
        boolean created = teacherDAO.createTeacher("Mehmet Yılmaz", "mehmetyilmaz", "12345");
        if (created) {
            System.out.println("Öğretmen başarıyla eklendi.");
        } else {
            System.out.println("Öğretmen eklenirken hata oluştu.");
        }

        // ID ile öğretmen çekme testi
        System.out.println("\nÖğretmen bilgileri alınıyor...");
        Teacher teacher = teacherDAO.getTeacherById(11);  // Burada 1 yerine veritabanında var olan bir id yaz
        if (teacher != null) {
            System.out.println("Öğretmen adı: " + teacher.getName());
            System.out.println("Öğretmen kullanıcı adı: " + teacher.getUsername());
        } else {
            System.out.println("Öğretmen bulunamadı.");
        }

        // Bağlantıyı kapatma
        DBConnection.closeConnection(connection);
    }
}
