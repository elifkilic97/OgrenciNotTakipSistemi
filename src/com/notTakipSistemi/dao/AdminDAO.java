package com.notTakipSistemi.dao;

import com.notTakipSistemi.model.Admin;

import java.sql.*;

public class AdminDAO {
    private Connection connection;

    public AdminDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createAdmin(String name, String username, String password) {
        if (isUsernameExists(username)) {
            System.out.println("Hata: Bu kullanıcı adı zaten var!");
            return false;
        }

        String insertUserSQL = "INSERT INTO Users (name, username, password, userType) VALUES (?, ?, ?, 'Admin')";
        String insertAdminSQL = "INSERT INTO Admins (id) VALUES (?)";

        try (PreparedStatement userStatement = connection.prepareStatement(insertUserSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            userStatement.setString(1, name);
            userStatement.setString(2, username);
            userStatement.setString(3, password);

            int affectedRows = userStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Kullanıcı eklenemedi.");

            try (ResultSet keys = userStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    int userId = keys.getInt(1);
                    try (PreparedStatement adminStatement = connection.prepareStatement(insertAdminSQL)) {
                        adminStatement.setInt(1, userId);
                        return adminStatement.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Admin getAdminById(int id) {
        String sql = "SELECT * FROM Users WHERE id = ? AND userType = 'Admin'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(id, rs.getString("name"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Admin getAdminByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ? AND userType = 'Admin'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                return new Admin(id, rs.getString("name"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Admin giriş hatası: " + e.getMessage());
        }
        return null;
    }

    // Admin bilgilerini güncelleme
    public boolean updateAdmin(int id, String name, String username, String password) {
        String sql = "UPDATE Users SET name = ?, username = ?, password = ? WHERE id = ? AND userType = 'Admin'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Admin silme işlemi
    public boolean deleteAdmin(int id) {
        String deleteFromAdmins = "DELETE FROM Admins WHERE id = ?";
        String deleteFromUsers = "DELETE FROM Users WHERE id = ? AND userType = 'Admin'";

        try (PreparedStatement stmt1 = connection.prepareStatement(deleteFromAdmins);
             PreparedStatement stmt2 = connection.prepareStatement(deleteFromUsers)) {

            stmt1.setInt(1, id);
            stmt2.setInt(1, id);

            int adminsAffected = stmt1.executeUpdate();
            int usersAffected = stmt2.executeUpdate();

            return adminsAffected > 0 && usersAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
