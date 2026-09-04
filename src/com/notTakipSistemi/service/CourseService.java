package com.notTakipSistemi.service;

import com.notTakipSistemi.dao.CourseDAO;
import com.notTakipSistemi.model.Course;

import java.sql.Connection;
import java.util.List;

public class CourseService {
    private CourseDAO courseDAO;

    public CourseService(Connection connection) {
        this.courseDAO = new CourseDAO(connection);
    }

    // 1. Belirli bir öğretmenin verdiği dersleri getir
    public List<Course> getCoursesByTeacherID(int teacherID) {
        return courseDAO.getCoursesByTeacherID(teacherID);
    }

    // 2. Ders ekle (isteğe bağlı kullanılabilir)
    public boolean addCourse(String courseName, int teacherID) {
        return courseDAO.addCourse(courseName, teacherID);
    }

    // 3. Ders sil (isteğe bağlı)
    public boolean deleteCourse(int courseID) {
        return courseDAO.deleteCourse(courseID);
    }

    // 4. ID'ye göre tek bir dersi getir
    public Course getCourseById(int courseID) {
        return courseDAO.getCourseById(courseID);
    }
}
