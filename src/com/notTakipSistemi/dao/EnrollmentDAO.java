package com.notTakipSistemi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentDAO {
    private Connection connection;

    public EnrollmentDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean enrollStudentToCourse(int studentId, int courseId) {
        // Önce zaten kayıtlı mı kontrol et
        String checkSql = "SELECT * FROM Course_Student WHERE studentID = ? AND courseID = ?";
        String insertSql = "INSERT INTO Course_Student (studentID, courseID) VALUES (?, ?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, studentId);
            checkStmt.setInt(2, courseId);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false; // zaten kayıtlı
            }

            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                insertStmt.setInt(1, studentId);
                insertStmt.setInt(2, courseId);
                return insertStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
