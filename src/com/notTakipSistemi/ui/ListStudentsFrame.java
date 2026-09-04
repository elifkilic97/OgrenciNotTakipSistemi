package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.StudentDAO;
import com.notTakipSistemi.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ListStudentsFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private StudentDAO studentDAO;

    public ListStudentsFrame() {
        setTitle("Öğrencileri Listele / Sil / Güncelle");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Veritabanı bağlantısı
        Connection connection = DBConnection.getConnection();
        studentDAO = new StudentDAO(connection);

        // Tablo başlıkları
        String[] columnNames = {"ID", "Ad Soyad", "Kullanıcı Adı", "Öğrenci No"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        loadStudents();

        // Butonlar
        JButton updateButton = new JButton("Seçili Öğrenciyi Güncelle");
        updateButton.addActionListener(e -> updateSelectedStudent());

        JButton deleteButton = new JButton("Seçili Öğrenciyi Sil");
        deleteButton.addActionListener(e -> deleteSelectedStudent());

        JButton detailButton = new JButton("Detayları Göster");
        detailButton.addActionListener(e -> showStudentDetail());

        // Yatay hizalama için FlowLayout kullan
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(detailButton);

        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadStudents() {
        tableModel.setRowCount(0); // tabloyu temizle
        List<Student> students = studentDAO.getAllStudents();
        for (Student s : students) {
            Object[] row = {s.getId(), s.getName(), s.getUsername(), s.getStudentID()};
            tableModel.addRow(row);
        }
    }

    private void deleteSelectedStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bu öğrenciyi silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = studentDAO.deleteStudent(studentId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Öğrenci başarıyla silindi.");
                    loadStudents(); // tabloyu yenile
                } else {
                    JOptionPane.showMessageDialog(this, "Silme işlemi başarısız oldu.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek öğrenciyi seçin.");
        }
    }

    private void updateSelectedStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            Student student = studentDAO.getStudentById(studentId);
            if (student != null) {
                UpdateStudentFrame updateFrame = new UpdateStudentFrame(student, this::loadStudents);
                updateFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci bilgisi alınamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen güncellenecek öğrenciyi seçin.");
        }
    }

    private void showStudentDetail() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            Student student = studentDAO.getStudentById(studentId);
            if (student != null) {
                StudentDetailPopupFrame detailFrame = new StudentDetailPopupFrame(student);
                detailFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci bilgisi alınamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen detaylarını görmek istediğiniz öğrenciyi seçin.");
        }
    }
}
