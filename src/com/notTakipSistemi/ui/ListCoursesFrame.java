package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.CourseDAO;
import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ListCoursesFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private CourseDAO courseDAO;

    public ListCoursesFrame() {
        setTitle("Dersleri Listele / Sil");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // DAO bağlantısı
        Connection connection = DBConnection.getConnection();
        courseDAO = new CourseDAO(connection);

        // Tablo başlıkları
        String[] columnNames = {"Ders ID", "Ders Adı", "Öğretmen Adı"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        loadCourses();

        // Silme butonu
        JButton deleteButton = new JButton("Seçili Dersi Sil");
        deleteButton.addActionListener(e -> deleteSelectedCourse());

        // Buton paneli
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.add(deleteButton);

        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadCourses() {
        tableModel.setRowCount(0); // tabloyu temizle
        List<Course> courses = courseDAO.getAllCoursesWithTeachers(); // 🔥 yeni metot kullanılıyor
        for (Course c : courses) {
            Object[] row = {
                    c.getCourseID(),
                    c.getCourseName(),
                    c.getTeacher().getName()
            };
            tableModel.addRow(row);
        }
    }

    private void deleteSelectedCourse() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int courseId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bu dersi silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = courseDAO.deleteCourse(courseId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Ders başarıyla silindi.");
                    loadCourses(); // tabloyu yenile
                } else {
                    JOptionPane.showMessageDialog(this, "Silme işlemi başarısız oldu.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek dersi seçin.");
        }
    }
}
