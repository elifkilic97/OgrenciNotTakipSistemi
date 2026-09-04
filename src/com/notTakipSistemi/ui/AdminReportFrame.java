package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.CourseDAO;
import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.StudentDAO;
import com.notTakipSistemi.dao.TeacherDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AdminReportFrame extends JFrame {

    public AdminReportFrame() {
        setTitle("Admin Rapor Paneli");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Veritabanı bağlantısı ve DAO'lar
        Connection connection = DBConnection.getConnection();
        StudentDAO studentDAO = new StudentDAO(connection);
        TeacherDAO teacherDAO = new TeacherDAO(connection);
        CourseDAO courseDAO = new CourseDAO(connection);

        int totalStudents = studentDAO.countStudents();
        int totalTeachers = teacherDAO.countTeachers();
        int totalCourses = courseDAO.countCourses();

        // İçerik paneli
        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        statsPanel.add(new JLabel("👩‍🎓 Toplam Öğrenci Sayısı: " + totalStudents));
        statsPanel.add(new JLabel("👨‍🏫 Toplam Öğretmen Sayısı: " + totalTeachers));
        statsPanel.add(new JLabel("📚 Toplam Ders Sayısı: " + totalCourses));

        add(statsPanel, BorderLayout.CENTER);
    }
}
