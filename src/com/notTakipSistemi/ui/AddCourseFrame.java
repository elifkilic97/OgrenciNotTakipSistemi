package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.CourseDAO;
import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.TeacherDAO;
import com.notTakipSistemi.model.Teacher;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class AddCourseFrame extends JFrame {

    private JTextField courseNameField;
    private JComboBox<Teacher> teacherComboBox;

    public AddCourseFrame() {
        setTitle("Ders Ekleme Paneli");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        Connection conn = DBConnection.getConnection();
        TeacherDAO teacherDAO = new TeacherDAO(conn);
        CourseDAO courseDAO = new CourseDAO(conn);

        List<Teacher> teacherList = teacherDAO.getAllTeachers(); // Eğer bu metod yoksa birazdan yazacağız
        teacherComboBox = new JComboBox<>(teacherList.toArray(new Teacher[0]));

        courseNameField = new JTextField();
        JButton saveButton = new JButton("Ekle");

        add(new JLabel("Ders Adı:"));
        add(courseNameField);

        add(new JLabel("Öğretmen Seç:"));
        add(teacherComboBox);

        add(new JLabel());
        add(saveButton);

        saveButton.addActionListener(e -> {
            String courseName = courseNameField.getText();
            Teacher selectedTeacher = (Teacher) teacherComboBox.getSelectedItem();

            if (courseName.isEmpty() || selectedTeacher == null) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun.");
                return;
            }

            boolean success = courseDAO.createCourse(courseName, selectedTeacher.getId());

            if (success) {
                JOptionPane.showMessageDialog(this, "Ders başarıyla eklendi!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Ders eklenemedi.");
            }
        });
    }
}
