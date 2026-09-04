package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.StudentDAO;
import com.notTakipSistemi.model.Student;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class UpdateStudentFrame extends JFrame {
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final Student student;
    private final StudentDAO studentDAO;
    private final Runnable refreshCallback; // 🔄 tabloyu yenilemek için

    public UpdateStudentFrame(Student student, Runnable refreshCallback) {
        this.student = student;
        this.refreshCallback = refreshCallback;

        Connection connection = DBConnection.getConnection();
        this.studentDAO = new StudentDAO(connection);

        setTitle("Öğrenci Güncelle");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Form alanları
        add(new JLabel("Ad Soyad:"));
        nameField = new JTextField(student.getName());
        add(nameField);

        add(new JLabel("Kullanıcı Adı:"));
        usernameField = new JTextField(student.getUsername());
        add(usernameField);

        add(new JLabel("Şifre:"));
        passwordField = new JPasswordField(student.getPassword());
        add(passwordField);

        // Butonlar
        JButton updateButton = new JButton("Güncelle");
        updateButton.addActionListener(e -> updateStudent());
        add(updateButton);

        JButton cancelButton = new JButton("İptal");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void updateStudent() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        boolean updated = studentDAO.updateStudent(student.getId(), name, username, password);
        if (updated) {
            JOptionPane.showMessageDialog(this, "Öğrenci başarıyla güncellendi.");
            if (refreshCallback != null) {
                refreshCallback.run(); // 🔄 ListStudentsFrame → loadStudents()
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Güncelleme başarısız oldu.");
        }
    }
}
