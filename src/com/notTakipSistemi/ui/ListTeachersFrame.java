package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.TeacherDAO;
import com.notTakipSistemi.model.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ListTeachersFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private TeacherDAO teacherDAO;

    public ListTeachersFrame() {
        setTitle("Öğretmenleri Listele / Sil / Güncelle");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Veritabanı bağlantısı
        Connection connection = DBConnection.getConnection();
        teacherDAO = new TeacherDAO(connection);

        // Tablo başlıkları
        String[] columnNames = {"ID", "Ad Soyad", "Kullanıcı Adı", "Öğretmen No"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        loadTeachers();

        // Güncelle, sil ve detay butonları
        JButton updateButton = new JButton("Seçili Öğretmeni Güncelle");
        updateButton.addActionListener(e -> updateSelectedTeacher());

        JButton deleteButton = new JButton("Seçili Öğretmeni Sil");
        deleteButton.addActionListener(e -> deleteSelectedTeacher());

        JButton detailButton = new JButton("Detayları Göster");
        detailButton.addActionListener(e -> showTeacherDetail());

        // Yatay hizalama için FlowLayout
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

    private void loadTeachers() {
        tableModel.setRowCount(0); // tabloyu temizle
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        for (Teacher t : teachers) {
            Object[] row = {t.getId(), t.getName(), t.getUsername(), t.getTeacherID()};
            tableModel.addRow(row);
        }
    }

    private void deleteSelectedTeacher() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int teacherId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bu öğretmeni silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = teacherDAO.deleteTeacher(teacherId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Öğretmen başarıyla silindi.");
                    loadTeachers(); // tabloyu yenile
                } else {
                    JOptionPane.showMessageDialog(this, "Silme işlemi başarısız oldu.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek öğretmeni seçin.");
        }
    }

    private void updateSelectedTeacher() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int teacherId = (int) tableModel.getValueAt(selectedRow, 0);
            Teacher teacher = teacherDAO.getTeacherById(teacherId);
            if (teacher != null) {
                UpdateTeacherFrame updateFrame = new UpdateTeacherFrame(teacher, this::loadTeachers);
                updateFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Öğretmen bilgisi alınamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen güncellenecek öğretmeni seçin.");
        }
    }

    private void showTeacherDetail() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int teacherId = (int) tableModel.getValueAt(selectedRow, 0);
            Teacher teacher = teacherDAO.getTeacherById(teacherId);
            if (teacher != null) {
                TeacherDetailPopupFrame detailFrame = new TeacherDetailPopupFrame(teacher);
                detailFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Öğretmen bilgisi alınamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen detaylarını görmek istediğiniz öğretmeni seçin.");
        }
    }
}
