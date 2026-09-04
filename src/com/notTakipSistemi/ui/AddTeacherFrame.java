package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.TeacherDAO;
import com.notTakipSistemi.model.Teacher;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AddTeacherFrame extends JFrame {

    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AddTeacherFrame() {
        setTitle("Öğretmen Ekleme Paneli");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Form bileşenleri
        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton saveButton = new JButton("Ekle");

        add(new JLabel("Ad Soyad:"));
        add(nameField);

        add(new JLabel("Kullanıcı Adı:"));
        add(usernameField);

        add(new JLabel("Şifre:"));
        add(passwordField);

        add(new JLabel()); // boşluk
        add(saveButton);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun.");
                return;
            }

            Connection conn = DBConnection.getConnection();
            TeacherDAO teacherDAO = new TeacherDAO(conn);

            boolean success = teacherDAO.createTeacher(name, username, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "Öğretmen başarıyla eklendi!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Kayıt başarısız! Kullanıcı adı zaten kullanılıyor olabilir.");
            }
        });
    }
}
