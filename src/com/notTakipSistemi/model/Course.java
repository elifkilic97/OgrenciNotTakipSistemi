package com.notTakipSistemi.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int courseID;
    private String courseName;
    private Teacher teacher;
    private List<Student> students;

    public Course(int courseID, String courseName, Teacher teacher) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.teacher = teacher;
        this.students = new ArrayList<>();
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return courseName;
    }

}

