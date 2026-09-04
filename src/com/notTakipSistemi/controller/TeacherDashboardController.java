// Controller sınıfı
package com.notTakipSistemi.controller;
import com.notTakipSistemi.ui.TeacherDashboardFrame;

import com.notTakipSistemi.model.Teacher;

public class TeacherDashboardController {

    private TeacherDashboardFrame dashboard;


    public TeacherDashboardController(Teacher teacher) {
        this.dashboard = new TeacherDashboardFrame(teacher);

        //this.dashboard.getUpdateButton().addActionListener(e -> updateStudentGrade());
    }

    public TeacherDashboardFrame getDashboard() {
        return dashboard;
    }


    private void updateStudentGrade() {
        System.out.println("Öğrenci notları güncelleniyor...");
    }
}
