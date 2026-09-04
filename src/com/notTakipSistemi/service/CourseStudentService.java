package com.notTakipSistemi.service;

import com.notTakipSistemi.dao.CourseStudentDAO;
import com.notTakipSistemi.model.Student;

import java.sql.Connection;
import java.util.List;

public class CourseStudentService {
    private CourseStudentDAO courseStudentDAO;

    public CourseStudentService(Connection connection) {
        this.courseStudentDAO = new CourseStudentDAO(connection);
    }

    // Belirli bir derse kayıtlı öğrencileri getir
    public List<Student> getStudentsByCourseID(int courseID) {
        return courseStudentDAO.getStudentsByCourseID(courseID);
    }
}
