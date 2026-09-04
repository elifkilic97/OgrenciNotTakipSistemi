package com.notTakipSistemi.dao;

import com.notTakipSistemi.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private Connection connection;

    public TeacherDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createTeacher(String name, String username, String password) {
        if (isUsernameExists(username)) {
            System.out.println("Hata: Bu kullanıcı adı zaten kullanımda!");
            return false;
        }

        String insertUserSql = "INSERT INTO Users (name, username, password, userType) VALUES (?, ?, ?, 'Teacher')";
        String insertTeacherSql = "INSERT INTO Teachers (id, teacherID) VALUES (?, ?)";

        try (PreparedStatement userStatement = connection.prepareStatement(insertUserSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            userStatement.setString(1, name);
            userStatement.setString(2, username);
            userStatement.setString(3, password);
            int affectedRows = userStatement.executeUpdate();

            if (affectedRows == 0) throw new SQLException("Kullanıcı oluşturulamadı.");

            try (ResultSet generatedKeys = userStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    int newTeacherID = getNextTeacherID();

                    try (PreparedStatement teacherStatement = connection.prepareStatement(insertTeacherSql)) {
                        teacherStatement.setInt(1, userId);
                        teacherStatement.setInt(2, newTeacherID);
                        teacherStatement.executeUpdate();
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getNextTeacherID() {
        String sql = "SELECT MAX(teacherID) AS maxID FROM Teachers";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                return maxID + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 100;  // Başlangıç değeri
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

    public Teacher getTeacherById(int id) {
        String sql = "SELECT u.*, t.teacherID FROM Users u LEFT JOIN Teachers t ON u.id = t.id WHERE u.id = ? AND userType = 'Teacher'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int teacherID = rs.getInt("teacherID");
                return new Teacher(id, teacherID, name, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Teacher getTeacherByUsernameAndPassword(String username, String password) {
        String sql = "SELECT u.*, t.teacherID FROM Users u LEFT JOIN Teachers t ON u.id = t.id WHERE u.username = ? AND u.password = ? AND u.userType = 'Teacher'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int teacherID = rs.getInt("teacherID");
                return new Teacher(id, teacherID, rs.getString("name"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Öğretmen giriş hatası: " + e.getMessage());
        }
        return null;
    }

    // Öğretmen güncelleme
    public boolean updateTeacher(int id, String name, String username, String password) {
        String sql = "UPDATE Users SET name = ?, username = ?, password = ? WHERE id = ?";
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

    // Öğretmen silme
    public boolean deleteTeacher(int id) {
        String sql = "DELETE FROM Teachers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT u.id, u.name, u.username, u.password, t.teacherID " +
                "FROM Users u JOIN Teachers t ON u.id = t.id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int teacherID = rs.getInt("teacherID");

                teachers.add(new Teacher(id, teacherID, name, username, password));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }

    public int countTeachers() {
        String sql = "SELECT COUNT(*) FROM Teachers";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
