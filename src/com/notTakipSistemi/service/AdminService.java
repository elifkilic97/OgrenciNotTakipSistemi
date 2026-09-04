package com.notTakipSistemi.service;

import com.notTakipSistemi.dao.AdminDAO;
import com.notTakipSistemi.model.Admin;

import java.sql.Connection;

public class AdminService {
    private AdminDAO adminDAO;

    public AdminService(Connection connection) {
        this.adminDAO = new AdminDAO(connection);
    }

    public Admin createAdmin(String name, String username, String password) {
        return adminDAO.createAdmin(name, username, password) ?
                adminDAO.getAdminByUsernameAndPassword(username, password) : null;
    }

    public Admin getAdminById(int id) {
        return adminDAO.getAdminById(id);
    }

    public Admin getAdminByUsernameAndPassword(String username, String password) {
        return adminDAO.getAdminByUsernameAndPassword(username, password);
    }

    public boolean updateAdmin(int id, String name, String username, String password) {
        return adminDAO.updateAdmin(id, name, username, password);
    }

    public boolean deleteAdmin(int id) {
        return adminDAO.deleteAdmin(id);
    }

}
