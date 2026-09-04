package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.model.Course;
import com.notTakipSistemi.model.Grade;
import com.notTakipSistemi.model.Student;
import com.notTakipSistemi.model.Teacher;
import com.notTakipSistemi.service.CourseService;
import com.notTakipSistemi.service.CourseStudentService;
import com.notTakipSistemi.service.GradeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class TeacherDashboardFrame extends JFrame {
    private JComboBox<Course> courseComboBox;
    private JLabel selectedCourseLabel;
    private JLabel selectedStudentLabel;

    private Teacher teacher;
    private CourseService courseService;
    private CourseStudentService courseStudentService;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    private JTextField midtermField;
    private JTextField finalField;
    private JButton saveGradeButton;

    public TeacherDashboardFrame(Teacher teacher) {
        this.teacher = teacher;
        Connection connection = DBConnection.getConnection();
        this.courseService = new CourseService(connection);
        this.courseStudentService = new CourseStudentService(connection);

        setTitle("Öğretmen Paneli - " + teacher.getName() + " (Öğretmen No: " + teacher.getTeacherID() + ")");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadCourses();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coursePanel.add(new JLabel("Ders Listesi:"));
        courseComboBox = new JComboBox<>();
        JButton showCourseButton = new JButton("Dersi Seç");
        coursePanel.add(courseComboBox);
        coursePanel.add(showCourseButton);
        mainPanel.add(coursePanel);

        selectedStudentLabel = new JLabel("Seçili öğrenci: -");
        selectedStudentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectedStudentLabel.setForeground(new Color(0, 102, 204));
        selectedStudentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(selectedStudentLabel);

        JPanel notPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        notPanel.setBorder(BorderFactory.createTitledBorder("Not Girişi"));
        notPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        midtermField = new JTextField();
        finalField = new JTextField();
        saveGradeButton = new JButton("Notu Kaydet");

        notPanel.add(new JLabel("Vize Notu:"));
        notPanel.add(midtermField);
        notPanel.add(new JLabel("Final Notu:"));
        notPanel.add(finalField);
        notPanel.add(new JLabel());
        notPanel.add(saveGradeButton);

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(notPanel);

        // ✅ Kolonlar: ID (Users.id), Öğrenci No (Students.studentID)
        String[] columnNames = {"ID", "Öğrenci No", "Ad Soyad", "Kullanıcı Adı", "Vize", "Final", "Ortalama", "Durum"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(850, 250));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(scrollPane);

        selectedCourseLabel = new JLabel("Henüz ders seçilmedi.");
        selectedCourseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(selectedCourseLabel);

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow >= 0) {
                String name = (String) tableModel.getValueAt(selectedRow, 2); // Ad Soyad
                selectedStudentLabel.setText("Seçili öğrenci: " + name);

                Object midtermObj = tableModel.getValueAt(selectedRow, 4);
                Object finalObj = tableModel.getValueAt(selectedRow, 5);

                midtermField.setText((midtermObj != null && !midtermObj.equals("-")) ? midtermObj.toString() : "");
                finalField.setText((finalObj != null && !finalObj.equals("-")) ? finalObj.toString() : "");
            }
        });

        showCourseButton.addActionListener(e -> {
            Course selected = (Course) courseComboBox.getSelectedItem();
            if (selected != null) {
                selectedCourseLabel.setText("Seçilen Ders: " + selected.getCourseName());
                List<Student> students = courseStudentService.getStudentsByCourseID(selected.getCourseID());
                tableModel.setRowCount(0);

                for (Student student : students) {
                    Grade grade = new GradeService(DBConnection.getConnection()).getGradeByStudentAndCourse(student.getId(), selected.getCourseID());

                    String midtermStr = "-", finalStr = "-", avgStr = "-", status = "-";

                    if (grade != null) {
                        double midterm = grade.getMidterm();
                        double finalExam = grade.getFinalExam();
                        double average = grade.calculateAverage();
                        midtermStr = String.valueOf(midterm);
                        finalStr = String.valueOf(finalExam);
                        avgStr = String.format("%.2f", average);
                        status = (finalExam >= 40 && average >= 50) ? "Başarılı" : "Başarısız";
                    }

                    // 👇 Her iki ID'yi de tabloya ekliyoruz
                    tableModel.addRow(new Object[]{
                            student.getId(),           // Users.id → DB işlemi için
                            student.getStudentID(),    // Öğrenci No → kullanıcıya gösterilir
                            student.getName(),
                            student.getUsername(),
                            midtermStr,
                            finalStr,
                            avgStr,
                            status
                    });
                }
            }
        });

        saveGradeButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Lütfen bir öğrenci seçin!");
                return;
            }

            try {
                double midterm = Double.parseDouble(midtermField.getText());
                double finalExam = Double.parseDouble(finalField.getText());

                if (midterm < 0 || midterm > 100 || finalExam < 0 || finalExam > 100) {
                    JOptionPane.showMessageDialog(null, "Notlar 0 ile 100 arasında olmalıdır.");
                    return;
                }

                int userId = (int) tableModel.getValueAt(selectedRow, 0); // 0 → Users.id
                Course selectedCourse = (Course) courseComboBox.getSelectedItem();
                GradeService gradeService = new GradeService(DBConnection.getConnection());

                boolean result = gradeService.addOrUpdateGrade(userId, selectedCourse.getCourseID(), midterm, finalExam);

                if (result) {
                    JOptionPane.showMessageDialog(null, "Not başarıyla kaydedildi!");

                    double average = (midterm * 0.4) + (finalExam * 0.6);
                    String avgStr = String.format("%.2f", average);
                    String status = (finalExam >= 40 && average >= 50) ? "Başarılı" : "Başarısız";

                    tableModel.setValueAt(midterm, selectedRow, 4);
                    tableModel.setValueAt(finalExam, selectedRow, 5);
                    tableModel.setValueAt(avgStr, selectedRow, 6);
                    tableModel.setValueAt(status, selectedRow, 7);

                    midtermField.setText("");
                    finalField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Not kaydedilemedi.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Lütfen geçerli sayılar girin.");
            }
        });

        add(mainPanel);
    }

    private void loadCourses() {
        List<Course> courses = courseService.getCoursesByTeacherID(teacher.getId());
        for (Course course : courses) {
            courseComboBox.addItem(course);
        }
    }
}
