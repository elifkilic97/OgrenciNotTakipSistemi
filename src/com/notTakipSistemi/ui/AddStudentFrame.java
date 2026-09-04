package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.StudentDAO;
import com.notTakipSistemi.model.Student;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AddStudentFrame extends JFrame {

    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AddStudentFrame() {
        setTitle("Öğrenci Ekleme Paneli");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Bileşenler
        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton saveButton = new JButton("Ekle");

        // Etiket ve alanlar
        add(new JLabel("Ad Soyad:"));
        add(nameField);

        add(new JLabel("Kullanıcı Adı:"));
        add(usernameField);

        add(new JLabel("Şifre:"));
        add(passwordField);

        add(new JLabel()); // boşluk
        add(saveButton);

        // Action
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun.");
                return;
            }

            Connection conn = DBConnection.getConnection();
            StudentDAO studentDAO = new StudentDAO(conn);

            Student created = studentDAO.createStudent(name, username, password);
            if (created != null) {
                JOptionPane.showMessageDialog(this, "Öğrenci başarıyla eklendi!");
                dispose(); // pencereyi kapat
            } else {
                JOptionPane.showMessageDialog(this, "Kayıt başarısız! Kullanıcı adı zaten var olabilir.");
            }
        });
    }
}
