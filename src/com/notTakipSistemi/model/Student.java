package com.notTakipSistemi.model;

import com.notTakipSistemi.ui.StudentDashboardFrame;

import java.util.List;
import java.util.Objects;

public class Student extends User {
    private int studentID;  // Öğrenci numarası (Enrollments tablosu ile ilişkilendirilecek)
    private List<String> courses; // Öğrencinin dersleri
    private Object[][] grades;    // Derslerin not bilgileri

    public Student(int id, String name, String username, String password, int studentID) {
        super(id, name, username, password);
        this.studentID = studentID;
    }

    @Override
    public void openDashboard() {
        new StudentDashboardFrame(this).setVisible(true);
    }

    @Override
    public void login() {
        System.out.println("Öğrenci giriş yaptı: " + this.username);
    }

    @Override
    public void logout() {
        System.out.println("Öğrenci çıkış yaptı: " + this.username);
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public Object[][] getGrades() {
        return grades;
    }

    public void setGrades(Object[][] grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return name + " (" + studentID + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
