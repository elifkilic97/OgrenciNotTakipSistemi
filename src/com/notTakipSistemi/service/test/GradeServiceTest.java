package com.notTakipSistemi.service.test;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.model.Grade;
import com.notTakipSistemi.service.GradeService;

import java.sql.Connection;
import java.util.List;

public class GradeServiceTest {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
        if (connection == null) {
            System.out.println("Veritabanı bağlantısı kurulamadı.");
            return;
        }

        GradeService service = new GradeService(connection);

        // Örnek test verisi (veritabanında zaten bu ID’lere sahip öğrenci ve ders olduğunu varsayalım)
        int studentID = 26;   // Students tablosundaki id
        int courseID = 2;    // Courses tablosundaki courseID

        // 1. Not ekleme/güncelleme
        boolean result = service.addOrUpdateGrade(studentID, courseID, 70.0, 80.0);
        System.out.println(result ? "✔ Not başarıyla eklendi/güncellendi." : "✘ Not işlemi başarısız.");

        // 2. Öğrencinin tüm notlarını listele
        System.out.println("\nÖğrencinin notları:");
        List<Grade> grades = service.getGradesByStudent(studentID);
        if (grades.isEmpty()) {
            System.out.println("Öğrencinin not kaydı bulunamadı.");
        } else {
            for (Grade grade : grades) {
                System.out.println(grade);
            }
        }

        DBConnection.closeConnection(connection);
    }
}
