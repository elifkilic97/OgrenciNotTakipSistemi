package com.notTakipSistemi.service;

import com.notTakipSistemi.dao.TeacherDAO;
import com.notTakipSistemi.model.Teacher;

import java.sql.Connection;

public class TeacherService {
    private TeacherDAO teacherDAO;

    public TeacherService(Connection connection) {
        this.teacherDAO = new TeacherDAO(connection);
    }

    public Teacher createTeacher(String name, String username, String password) {
        return teacherDAO.createTeacher(name, username, password) ?
                teacherDAO.getTeacherByUsernameAndPassword(username, password) : null;
    }

    public Teacher getTeacherById(int id) {
        return teacherDAO.getTeacherById(id);
    }

    public Teacher getTeacherByUsernameAndPassword(String username, String password) {
        return teacherDAO.getTeacherByUsernameAndPassword(username, password);
    }

    public boolean updateTeacher(int id, String name, String username, String password) {
        return teacherDAO.updateTeacher(id, name, username, password);
    }

    public boolean deleteTeacher(int id) {
        return teacherDAO.deleteTeacher(id);
    }

}
