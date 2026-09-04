package com.notTakipSistemi.model;

public class Grade {
    private double midterm;
    private double finalExam;
    private String courseName; // eklendi

    public Grade(double midterm, double finalExam, String courseName) {
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.courseName = courseName;
    }

    public double calculateAverage() {
        return (midterm * 0.4) + (finalExam * 0.6);
    }

    public String getStatus() {
        double average = calculateAverage();
        return (finalExam >= 40 && average >= 50) ? "Başarılı" : "Başarısız";
    }

    public double getMidterm() {
        return midterm;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return courseName + " | Vize: " + midterm + ", Final: " + finalExam +
                ", Ortalama: " + calculateAverage() +
                ", Durum: " + getStatus();
    }
}
