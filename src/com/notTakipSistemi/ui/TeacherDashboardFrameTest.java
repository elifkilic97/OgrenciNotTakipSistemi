package com.notTakipSistemi.ui;

import com.notTakipSistemi.model.Teacher;

public class TeacherDashboardFrameTest {
    public static void main(String[] args) {
        // testogretmen veritabanında kayıtlı olmalı
        Teacher testTeacher = new Teacher(27, 2002, "Test Öğretmen", "testogretmen", "1234");

        TeacherDashboardFrame frame = new TeacherDashboardFrame(testTeacher);
        frame.setVisible(true);
    }
}
