package com.notTakipSistemi.dao;

import com.notTakipSistemi.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseStudentDAO {
    private Connection connection;

    public CourseStudentDAO(Connection connection) {
        this.connection = connection;
    }

    // Bir derse kayıtlı tüm öğrencileri getir
    public List<Student> getStudentsByCourseID(int courseID) {
        List<Student> students = new ArrayList<>();

        String sql = "SELECT u.id, u.name, u.username, u.password, s.studentID " +
                "FROM Course_Student cs " +
                "JOIN Students s ON cs.studentID = s.id " +
                "JOIN Users u ON u.id = s.id " +
                "WHERE cs.courseID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int studentID = rs.getInt("studentID");

                students.add(new Student(id, name, username, password, studentID));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}
