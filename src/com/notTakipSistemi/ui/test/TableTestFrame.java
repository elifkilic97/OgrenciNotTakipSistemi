package com.notTakipSistemi.ui.test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableTestFrame extends JFrame {
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public TableTestFrame() {
        setTitle("Test JTable");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "Ad Soyad", "Kullanıcı Adı", "Öğrenci No"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Test satırı ekliyoruz
        tableModel.addRow(new Object[]{1, "Test Öğrenci", "testuser", 1234});

        studentTable = new JTable(tableModel);
        studentTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TableTestFrame().setVisible(true);
        });
    }
}
