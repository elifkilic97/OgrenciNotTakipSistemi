package com.notTakipSistemi.service.test;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.model.Student;
import com.notTakipSistemi.service.StudentService;

import java.sql.Connection;

public class StudentServiceTest {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();

        if (connection == null) {
            System.out.println("Veritabanı bağlantısı başarısız!");
            return;
        }

        StudentService service = new StudentService(connection);

        // 1. Yeni öğrenci oluştur
        Student newStudent = service.createStudent("Ali Yılmaz", "aliyilmaz", "password123");
        if (newStudent != null) {
            System.out.println("✔ Öğrenci oluşturuldu: " + newStudent);
        } else {
            System.out.println("✘ Öğrenci oluşturulamadı.");
            return;
        }

        int id = newStudent.getId();

        // 2. ID ile öğrenci bul
        Student found = service.getStudentById(id);
        System.out.println("🔍 ID ile bulunan öğrenci: " + found);

        // 3. Öğrenci bilgilerini güncelle
        boolean updated = service.updateStudent(id, "Ali Tas", "alitas", "yeniSifre456");
        if (updated) {
            System.out.println("🔁 Öğrenci güncellendi.");
        } else {
            System.out.println("✘ Öğrenci güncellenemedi.");
        }

        // 4. Güncellenmiş haliyle giriş yap
        Student loginStudent = service.getStudentByUsernameAndPassword("alitas", "yeniSifre456");
        System.out.println("🔑 Giriş yapan öğrenci: " + loginStudent);

        // 5. Öğrenci sil
        boolean deleted = service.deleteStudent(id);
        System.out.println(deleted ? "🗑️ Öğrenci silindi." : "✘ Silme başarısız.");

        // 6. Silindikten sonra tekrar arama
        Student afterDelete = service.getStudentById(id);
        System.out.println(afterDelete == null ? "✔ Öğrenci artık yok." : "✘ Silinmemiş gibi duruyor!");

        DBConnection.closeConnection(connection);
    }
}
