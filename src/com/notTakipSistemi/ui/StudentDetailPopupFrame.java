package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.StudentDAO;
import com.notTakipSistemi.model.Course;
import com.notTakipSistemi.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class StudentDetailPopupFrame extends JFrame {
    private Student student;
    private StudentDAO studentDAO;
    private DefaultTableModel tableModel;

    public StudentDetailPopupFrame(Student student) {
        this.student = student;
        Connection connection = DBConnection.getConnection();
        this.studentDAO = new StudentDAO(connection);

        setTitle("Öğrenci Detayları - " + student.getName());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Üstte öğrenci bilgileri
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Öğrenci Bilgileri"));
        infoPanel.add(new JLabel("Ad Soyad: " + student.getName()));
        infoPanel.add(new JLabel("Kullanıcı Adı: " + student.getUsername()));
        infoPanel.add(new JLabel("Şifre: " + student.getPassword()));
        add(infoPanel, BorderLayout.NORTH);

        // Dersleri gösteren tablo
        String[] columnNames = {"Ders ID", "Ders Adı", "Öğretmen Adı"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        loadCourses();
    }

    private void loadCourses() {
        List<Course> courses = studentDAO.getCoursesByStudentId(student.getId());
        for (Course course : courses) {
            Object[] row = {
                    course.getCourseID(),
                    course.getCourseName(),
                    course.getTeacher().getName()
            };
            tableModel.addRow(row);
        }
    }
}
