package com.notTakipSistemi.model;

import com.notTakipSistemi.ui.TeacherDashboardFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Teacher extends User {
    private int teacherID;  // Courses tablosu ile ilişkilendirilecek
    private List<String> courses; // Öğretmenin verdiği derslerin listesi

    public Teacher(int id, int teacherID, String name, String username, String password) {
        super(id, name, username, password);
        this.teacherID = teacherID;
        this.courses = new ArrayList<>();  // Başlangıçta boş bir liste
    }

    @Override
    public void openDashboard() {
        new TeacherDashboardFrame(this).setVisible(true);
    }

    @Override
    public void login() {
        System.out.println("Öğretmen giriş yaptı: " + this.username);
    }

    @Override
    public void logout() {
        System.out.println("Öğretmen çıkış yaptı: " + this.username);
    }

    public int getTeacherID() {
        return teacherID;
    }

    public List<String> getCourses() {
        return courses != null ? courses : new ArrayList<>();  // null kontrolü
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return name + " (" + teacherID + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Teacher teacher = (Teacher) obj;
        return id == teacher.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
