package com.notTakipSistemi.model;

import com.notTakipSistemi.ui.AdminDashboardFrame;

import java.util.Objects;

public class Admin extends User {
    public Admin(int id, String name, String username, String password) {
        super(id, name, username, password);
    }

    @Override
    public void openDashboard() {
        new AdminDashboardFrame(this).setVisible(true);
    }

    @Override
    public void login() {
        System.out.println("Yönetici giriş yaptı: " + this.username);
    }

    @Override
    public void logout() {
        System.out.println("Yönetici çıkış yaptı: " + this.username);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Admin admin = (Admin) obj;
        return id == admin.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
