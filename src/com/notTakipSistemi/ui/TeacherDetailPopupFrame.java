package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.CourseDAO;
import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.model.Course;
import com.notTakipSistemi.model.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class TeacherDetailPopupFrame extends JFrame {
    private Teacher teacher;
    private CourseDAO courseDAO;
    private DefaultTableModel tableModel;

    public TeacherDetailPopupFrame(Teacher teacher) {
        this.teacher = teacher;
        Connection connection = DBConnection.getConnection();
        this.courseDAO = new CourseDAO(connection);

        setTitle("Öğretmen Detayları - " + teacher.getName());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Üstte öğretmen bilgileri
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Öğretmen Bilgileri"));
        infoPanel.add(new JLabel("Ad Soyad: " + teacher.getName()));
        infoPanel.add(new JLabel("Kullanıcı Adı: " + teacher.getUsername()));
        infoPanel.add(new JLabel("Şifre: " + teacher.getPassword()));
        add(infoPanel, BorderLayout.NORTH);

        // Verdiği dersleri gösteren tablo
        String[] columnNames = {"Ders ID", "Ders Adı"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        loadCourses();
    }

    private void loadCourses() {
        List<Course> courses = courseDAO.getCoursesByTeacherID(teacher.getId()); // id = Users.id
        for (Course course : courses) {
            Object[] row = {
                    course.getCourseID(),
                    course.getCourseName()
            };
            tableModel.addRow(row);
        }
    }
}
