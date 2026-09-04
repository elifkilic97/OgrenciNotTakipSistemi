package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.TeacherDAO;
import com.notTakipSistemi.model.Teacher;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class UpdateTeacherFrame extends JFrame {
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final Teacher teacher;
    private final TeacherDAO teacherDAO;
    private final Runnable refreshCallback;

    public UpdateTeacherFrame(Teacher teacher, Runnable refreshCallback) {
        this.teacher = teacher;
        this.refreshCallback = refreshCallback;

        Connection connection = DBConnection.getConnection();
        this.teacherDAO = new TeacherDAO(connection);

        setTitle("Öğretmen Güncelle");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Ad Soyad:"));
        nameField = new JTextField(teacher.getName());
        add(nameField);

        add(new JLabel("Kullanıcı Adı:"));
        usernameField = new JTextField(teacher.getUsername());
        add(usernameField);

        add(new JLabel("Şifre:"));
        passwordField = new JPasswordField(teacher.getPassword());
        add(passwordField);

        JButton updateButton = new JButton("Güncelle");
        updateButton.addActionListener(e -> updateTeacher());
        add(updateButton);

        JButton cancelButton = new JButton("İptal");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void updateTeacher() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        boolean updated = teacherDAO.updateTeacher(teacher.getId(), name, username, password);
        if (updated) {
            JOptionPane.showMessageDialog(this, "Öğretmen başarıyla güncellendi.");
            if (refreshCallback != null) refreshCallback.run();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Güncelleme başarısız.");
        }
    }
}
