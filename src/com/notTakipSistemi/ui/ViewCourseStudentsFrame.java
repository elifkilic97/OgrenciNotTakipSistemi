package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.CourseDAO;
import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ViewCourseStudentsFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private CourseDAO courseDAO;
    private JComboBox<Course> courseComboBox;

    public ViewCourseStudentsFrame() {
        setTitle("Derse Kayıtlı Öğrenciler");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Connection connection = DBConnection.getConnection();
        courseDAO = new CourseDAO(connection);

        // Üst: Ders seçimi
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Ders Seç:"));
        courseComboBox = new JComboBox<>();
        for (Course c : courseDAO.getAllCourses()) {
            courseComboBox.addItem(c);
        }
        topPanel.add(courseComboBox);

        JButton showButton = new JButton("Öğrencileri Göster");
        showButton.addActionListener(e -> loadStudents());
        topPanel.add(showButton);
        add(topPanel, BorderLayout.NORTH);

        // Orta: Öğrenci tablosu
        String[] columnNames = {"Öğrenci Adı"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        if (selectedCourse == null) return;

        List<String> students = courseDAO.getEnrolledStudentsByCourseId(selectedCourse.getCourseID());
        for (String name : students) {
            tableModel.addRow(new Object[]{name});
        }
    }
}
