package com.notTakipSistemi.ui;

import com.notTakipSistemi.model.Admin;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame(Admin admin) {
        setTitle("Admin Paneli - " + admin.getName());
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Hoş geldiniz etiketi
        JLabel welcomeLabel = new JLabel("Hoşgeldiniz, " + admin.getName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        // Butonlar
        JButton addStudentButton = new JButton("Öğrenci Ekle");
        JButton addTeacherButton = new JButton("Öğretmen Ekle");
        JButton addCourseButton = new JButton("Ders Ekle");
        JButton assignCourseButton = new JButton("Öğrenciyi Derse Ekle");
        JButton listStudentsButton = new JButton("Öğrencileri Listele / Sil / Güncelle");
        JButton listTeachersButton = new JButton("Öğretmenleri Listele / Sil / Güncelle");
        JButton listCoursesButton = new JButton("Dersleri Listele / Sil");
        JButton viewCourseStudentsButton = new JButton("Dersin Öğrencilerini Listele / Çıkar");
        JButton reportButton = new JButton("Rapor Paneli"); // 🔥 yeni buton

        // Buton paneli
        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 20, 20)); // 9 buton = 5x2 düzen
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        buttonPanel.add(addStudentButton);
        buttonPanel.add(addTeacherButton);
        buttonPanel.add(addCourseButton);
        buttonPanel.add(assignCourseButton);
        buttonPanel.add(listStudentsButton);
        buttonPanel.add(listTeachersButton);
        buttonPanel.add(listCoursesButton);
        buttonPanel.add(viewCourseStudentsButton);
        buttonPanel.add(reportButton); // 🔥 yeni eklenen buton

        add(buttonPanel, BorderLayout.CENTER);

        // Buton olayları
        addStudentButton.addActionListener(e -> new AddStudentFrame().setVisible(true));
        addTeacherButton.addActionListener(e -> new AddTeacherFrame().setVisible(true));
        addCourseButton.addActionListener(e -> new AddCourseFrame().setVisible(true));
        assignCourseButton.addActionListener(e -> new AssignStudentToCourseFrame().setVisible(true));
        listStudentsButton.addActionListener(e -> new ListStudentsFrame().setVisible(true));
        listTeachersButton.addActionListener(e -> new ListTeachersFrame().setVisible(true));
        listCoursesButton.addActionListener(e -> new ListCoursesFrame().setVisible(true));
        viewCourseStudentsButton.addActionListener(e -> new ViewCourseStudentsFrame().setVisible(true));
        reportButton.addActionListener(e -> new AdminReportFrame().setVisible(true)); // 🔥 açma işlemi
    }
}
