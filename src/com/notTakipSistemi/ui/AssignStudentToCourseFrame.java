package com.notTakipSistemi.ui;

import com.notTakipSistemi.dao.CourseDAO;
import com.notTakipSistemi.dao.DBConnection;
import com.notTakipSistemi.dao.StudentDAO;
import com.notTakipSistemi.dao.EnrollmentDAO;
import com.notTakipSistemi.model.Course;
import com.notTakipSistemi.model.Student;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class AssignStudentToCourseFrame extends JFrame {

    private JComboBox<Student> studentComboBox;
    private JComboBox<Course> courseComboBox;

    public AssignStudentToCourseFrame() {
        setTitle("Öğrenciyi Derse Ekle");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        Connection connection = DBConnection.getConnection();
        StudentDAO studentDAO = new StudentDAO(connection);
        CourseDAO courseDAO = new CourseDAO(connection);
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO(connection); // kendi yazacağımız DAO sınıfı

        // Öğrenci ve Ders listeleri
        List<Student> students = studentDAO.getAllStudents();
        List<Course> courses = courseDAO.getAllCourses();

        studentComboBox = new JComboBox<>(students.toArray(new Student[0]));
        courseComboBox = new JComboBox<>(courses.toArray(new Course[0]));

        JButton assignButton = new JButton("EKLE");
        assignButton.addActionListener(e -> {
            Student selectedStudent = (Student) studentComboBox.getSelectedItem();
            Course selectedCourse = (Course) courseComboBox.getSelectedItem();

            if (selectedStudent != null && selectedCourse != null) {
                boolean success = enrollmentDAO.enrollStudentToCourse(selectedStudent.getId(), selectedCourse.getCourseID());
                if (success) {
                    JOptionPane.showMessageDialog(this, "Öğrenci derse başarıyla eklendi!");
                } else {
                    JOptionPane.showMessageDialog(this, "Zaten kayıtlı veya hata oluştu.");
                }
            }
        });

        add(new JLabel("Öğrenci Seç:"));
        add(studentComboBox);
        add(new JLabel("Ders Seç:"));
        add(courseComboBox);
        add(new JLabel());
        add(assignButton);
    }
}
