package com.notTakipSistemi.dao;

import com.notTakipSistemi.model.Admin;
import com.notTakipSistemi.model.Student;
import com.notTakipSistemi.model.Teacher;
import com.notTakipSistemi.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public User login(String username, String password) {
        String sql = "SELECT u.id, u.name, u.username, u.userType, s.studentID, t.teacherID " +
                "FROM Users u " +
                "LEFT JOIN Students s ON u.id = s.id " +
                "LEFT JOIN Teachers t ON u.id = t.id " +
                "WHERE u.username = ? AND u.password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // kullanıcı bulunmuş demektir.
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String userType = resultSet.getString("userType");

                switch (userType.toLowerCase()) {
                    case "student":
                        Integer studentID = (Integer) resultSet.getObject("studentID");
                        if (studentID != null) {
                            return new Student(id, name, username, password, studentID);
                        } else {
                            // Öğrenci için ID bulunamadığında ne yapılacağına karar ver
                            return null;
                        }
                    case "teacher":
                        Integer teacherID = (Integer) resultSet.getObject("teacherID");
                        if (teacherID != null) {
                            return new Teacher(id, teacherID, name, username, password);
                        } else {
                            // Öğretmen için ID bulunamadığında ne yapılacağına karar ver
                            return null;
                        }
                    case "admin":
                        return new Admin(id, name, username, password);
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
