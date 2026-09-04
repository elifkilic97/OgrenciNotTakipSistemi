package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.UserDAO;
import com.notTakipSistemi.model.Student;
import com.notTakipSistemi.model.User;
import com.notTakipSistemi.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Giriş Paneli");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Giriş Yap");

        panel.add(new JLabel("Kullanıcı Adı:"));
        panel.add(usernameField);
        panel.add(new JLabel("Şifre:"));
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        Connection connection = DBConnection.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Veritabanı bağlantısı başarısız.");
            return;
        }

        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Giriş başarılı: " + user.getUsername());

            if (user instanceof Student student) {
                StudentService studentService = new StudentService(DBConnection.getConnection());
                studentService.loadStudentGrades(student); // Notları veritabanından çek
                student.openDashboard(); // artık grades dolu, exception yok
            } else {
                user.openDashboard(); // Öğretmen & Admin için direkt geç
            }

            dispose(); // Login ekranını kapat
        }
        else {
            JOptionPane.showMessageDialog(this, "Kullanıcı adı veya şifre hatalı.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
