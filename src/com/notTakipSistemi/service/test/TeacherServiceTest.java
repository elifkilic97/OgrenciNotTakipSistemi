package com.notTakipSistemi.service.test;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.model.Teacher;
import com.notTakipSistemi.service.TeacherService;

import java.sql.Connection;

public class TeacherServiceTest {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
        if (connection == null) {
            System.out.println("Veritabanı bağlantısı kurulamadı.");
            return;
        }

        TeacherService service = new TeacherService(connection);

        // 1. Yeni öğretmen oluştur
        System.out.println("Yeni öğretmen oluşturuluyor...");
        Teacher newTeacher = service.createTeacher("Mehmet Demir", "mehmetdemir", "abc123");

        if (newTeacher == null) {
            System.out.println("✘ Öğretmen oluşturulamadı.");
            DBConnection.closeConnection(connection);
            return;
        }

        System.out.println("✔ Oluşturulan öğretmen: " + newTeacher);
        int id = newTeacher.getId();

        // 2. ID ile öğretmen getir
        System.out.println("\nID ile öğretmen getiriliyor...");
        Teacher found = service.getTeacherById(id);
        System.out.println(found != null ? "✔ Bulunan öğretmen: " + found : "✘ Öğretmen bulunamadı.");

        // 3. Öğretmen bilgilerini güncelle
        System.out.println("\nÖğretmen bilgileri güncelleniyor...");
        boolean updated = service.updateTeacher(id, "M. Demir", "m.demir", "456xyz");
        System.out.println(updated ? "✔ Güncelleme başarılı." : "✘ Güncelleme başarısız.");

        // 4. Kullanıcı adı ve şifre ile giriş testi
        System.out.println("\nGiriş testi yapılıyor...");
        Teacher loginTeacher = service.getTeacherByUsernameAndPassword("m.demir", "456xyz");
        System.out.println(loginTeacher != null ? "✔ Giriş başarılı: " + loginTeacher : "✘ Giriş başarısız.");

        // 5. Öğretmen silme
        System.out.println("\nÖğretmen siliniyor...");
        boolean deleted = service.deleteTeacher(id);
        System.out.println(deleted ? "✔ Silme başarılı." : "✘ Silinemedi.");

        // 6. Silindikten sonra tekrar kontrol
        System.out.println("\nSilinen öğretmen tekrar aranıyor...");
        Teacher afterDelete = service.getTeacherById(id);
        System.out.println(afterDelete == null ? "✔ Öğretmen artık yok." : "✘ Silinmemiş gibi duruyor!");

        DBConnection.closeConnection(connection);
    }
}
