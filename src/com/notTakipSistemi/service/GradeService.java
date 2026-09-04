package com.notTakipSistemi.service;

import com.notTakipSistemi.dao.GradeDAO;
import com.notTakipSistemi.model.Grade;

import java.sql.Connection;
import java.util.List;

public class GradeService {
    private GradeDAO gradeDAO;

    public GradeService(Connection connection) {
        this.gradeDAO = new GradeDAO(connection);
    }

    public boolean addOrUpdateGrade(int studentID, int courseID, double midterm, double finalExam) {
        if (midterm < 0 || midterm > 100 || finalExam < 0 || finalExam > 100) {
            System.out.println("Hata: Notlar 0-100 arasında olmalıdır.");
            return false;
        }
        return gradeDAO.addOrUpdateGrade(studentID, courseID, midterm, finalExam);
    }

    public List<Grade> getGradesByStudent(int studentID) {
        return gradeDAO.getGradesByStudent(studentID);
    }

    public Grade getGradeByStudentAndCourse(int studentID, int courseID) {
        return gradeDAO.getGradeByStudentAndCourse(studentID, courseID);
    }

}
