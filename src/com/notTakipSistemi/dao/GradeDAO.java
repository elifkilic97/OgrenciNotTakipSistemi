package com.notTakipSistemi.dao;

import com.notTakipSistemi.model.Grade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    private Connection connection;

    public GradeDAO(Connection connection) {
        this.connection = connection;
    }

    // Vize ve Final notu ekleme veya güncelleme
    public boolean addOrUpdateGrade(int studentID, int courseID, double midterm, double finalExam) {
        String checkSql = "SELECT gradeID FROM Grades WHERE studentID = ? AND courseID = ?";
        String insertSql = "INSERT INTO Grades (studentID, courseID, midterm, [final]) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE Grades SET midterm = ?, final = ? WHERE studentID = ? AND courseID = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, studentID);
            checkStmt.setInt(2, courseID);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Güncelle
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setDouble(1, midterm);
                    updateStmt.setDouble(2, finalExam);
                    updateStmt.setInt(3, studentID);
                    updateStmt.setInt(4, courseID);
                    return updateStmt.executeUpdate() > 0;
                }
            } else {
                // Ekle
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, studentID);
                    insertStmt.setInt(2, courseID);
                    insertStmt.setDouble(3, midterm);
                    insertStmt.setDouble(4, finalExam);
                    return insertStmt.executeUpdate() > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Belirli bir öğrenciye ait tüm notları getir
    public List<Grade> getGradesByStudent(int studentID) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT g.midterm, g.final, c.courseName FROM Grades g " +
                "JOIN Courses c ON g.courseID = c.courseID " +
                "WHERE g.studentID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double midterm = rs.getDouble("midterm");
                double finalExam = rs.getDouble("final");
                String courseName = rs.getString("courseName");
                grades.add(new Grade(midterm, finalExam, courseName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grades;
    }

    public Grade getGradeByStudentAndCourse(int studentID, int courseID) {
        String sql = "SELECT midterm, final FROM Grades WHERE studentID = ? AND courseID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentID);
            stmt.setInt(2, courseID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double midterm = rs.getDouble("midterm");
                double finalExam = rs.getDouble("final");
                return new Grade(midterm, finalExam, null); // courseName null olabilir
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
