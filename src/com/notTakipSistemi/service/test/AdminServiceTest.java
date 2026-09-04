package com.notTakipSistemi.service.test;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.model.Admin;
import com.notTakipSistemi.service.AdminService;

import java.sql.Connection;

public class AdminServiceTest {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
        if (connection == null) {
            System.out.println("Veritabanı bağlantısı başarısız.");
            return;
        }

        AdminService service = new AdminService(connection);

        // 1. Yeni admin oluştur
        System.out.println("Yeni admin oluşturuluyor...");
        Admin newAdmin = service.createAdmin("Kemal Admin", "kemaladmin", "pass123");

        if (newAdmin == null) {
            System.out.println("✘ Admin oluşturulamadı.");
            DBConnection.closeConnection(connection);
            return;
        }

        System.out.println("✔ Oluşturulan admin: " + newAdmin);
        int id = newAdmin.getId();

        // 2. ID ile admin getir
        System.out.println("\nID ile admin bilgisi getiriliyor...");
        Admin found = service.getAdminById(id);
        System.out.println(found != null ? "✔ Admin bulundu: " + found : "✘ Admin bulunamadı.");

        // 3. Admin bilgilerini güncelle
        System.out.println("\nAdmin bilgileri güncelleniyor...");
        boolean updated = service.updateAdmin(id, "Kemal Aydın", "kemalaydin", "newpass123");
        System.out.println(updated ? "✔ Güncelleme başarılı." : "✘ Güncelleme başarısız.");

        // 4. Güncellenmiş bilgilerle giriş testi
        System.out.println("\nYeni bilgilerle giriş yapılıyor...");
        Admin loginAdmin = service.getAdminByUsernameAndPassword("kemalaydin", "newpass123");
        System.out.println(loginAdmin != null ? "✔ Giriş başarılı: " + loginAdmin : "✘ Giriş başarısız.");

        // 5. Admin silme
        System.out.println("\nAdmin siliniyor...");
        boolean deleted = service.deleteAdmin(id);
        System.out.println(deleted ? "✔ Silme başarılı." : "✘ Silme başarısız.");

        // 6. Silindikten sonra tekrar kontrol
        System.out.println("\nSilinen admin tekrar aranıyor...");
        Admin afterDelete = service.getAdminById(id);
        System.out.println(afterDelete == null ? "✔ Admin artık yok." : "✘ Silinmemiş gibi duruyor!");

        DBConnection.closeConnection(connection);
    }
}
