package com.notTakipSistemi.service;

import com.notTakipSistemi.dao.StudentDAO;
import com.notTakipSistemi.model.Student;
import com.notTakipSistemi.dao.GradeDAO;
import com.notTakipSistemi.model.Grade;

import java.sql.Connection;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;
    private GradeDAO gradeDAO; // notları da çekmek için

    public StudentService(Connection connection) {
        this.studentDAO = new StudentDAO(connection);
        this.gradeDAO = new GradeDAO(connection); // grade işlemleri için
    }

    public Student createStudent(String name, String username, String password) {
        return studentDAO.createStudent(name, username, password);
    }

    public Student getStudentById(int studentId) {
        return studentDAO.getStudentById(studentId);
    }

    public boolean updateStudent(int id, String name, String username, String password) {
        return studentDAO.updateStudent(id, name, username, password);
    }

    public boolean deleteStudent(int id) {
        return studentDAO.deleteStudent(id);
    }

    public Student getStudentByUsernameAndPassword(String username, String password) {
        return studentDAO.getStudentByUsernameAndPassword(username, password);
    }

    // 👇👇👇 EKLEDİĞİMİZ KISIM 👇👇👇
    public List<Grade> viewGrades(int studentID) {
        return gradeDAO.getGradesByStudent(studentID);
    }

    public void loadStudentGrades(Student student) {
        List<Grade> gradeList = gradeDAO.getGradesByStudent(student.getId()); // ✅ doğru: Users tablosundaki id


        Object[][] gradesTable = new Object[gradeList.size()][5]; // 5 sütun: Ders Adı, Vize, Final, Ortalama, Durum

        for (int i = 0; i < gradeList.size(); i++) {
            Grade grade = gradeList.get(i);
            gradesTable[i][0] = grade.getCourseName();
            gradesTable[i][1] = grade.getMidterm();
            gradesTable[i][2] = grade.getFinalExam();
            gradesTable[i][3] = grade.calculateAverage();
            gradesTable[i][4] = grade.getStatus();
        }

        student.setGrades(gradesTable);
    }

}
