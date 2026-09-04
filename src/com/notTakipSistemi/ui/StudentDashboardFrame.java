package com.notTakipSistemi.ui;

import com.notTakipSistemi.model.Student;

import javax.swing.*;
import java.awt.*;

public class StudentDashboardFrame extends JFrame {

    public StudentDashboardFrame(Student student) {
        setTitle("Öğrenci Paneli - " + student.getName());
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Başlık
        JLabel welcomeLabel = new JLabel("Hoşgeldin, " + student.getName() +
                " | Öğrenci No: " + student.getStudentID());

        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Sütun adları
        String[] columnNames = {"Ders Adı", "Vize", "Final", "Ortalama", "Durum"};

        // Not verileri
        Object[][] data = student.getGrades();

        // JTable ve JScrollPane
        JTable gradeTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(gradeTable);

        // Çıkış butonu
        JButton logoutButton = new JButton("Çıkış Yap");
        logoutButton.addActionListener(e -> {
            dispose();
            System.exit(0);
        });

        // Alt panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutButton);

        // Ana layout
        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
