package com.notTakipSistemi.dao;

import com.notTakipSistemi.model.Course;
import com.notTakipSistemi.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }

    //Belirli bir öğretmenin verdiği dersleri getir
    public List<Course> getCoursesByTeacherID(int teacherID) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.courseID, c.courseName, t.teacherID, u.id, u.name, u.username, u.password " +
                "FROM Courses c " +
                "JOIN Teachers t ON c.teacherID = t.id " +
                "JOIN Users u ON u.id = t.id " +
                "WHERE t.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, teacherID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int courseID = rs.getInt("courseID");
                String courseName = rs.getString("courseName");

                // Öğretmeni de Course nesnesine ekle
                Teacher teacher = new Teacher(
                        rs.getInt("id"),
                        rs.getInt("teacherID"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password")
                );

                Course course = new Course(courseID, courseName, teacher);
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    // Ders ekleme
    public boolean addCourse(String courseName, int teacherID) {
        String sql = "INSERT INTO Courses (courseName, teacherID) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, courseName);
            stmt.setInt(2, teacherID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Ders silme
    public boolean deleteCourse(int courseID) {
        String sql = "DELETE FROM Courses WHERE courseID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Tek bir dersi ID'ye göre getir
    public Course getCourseById(int courseID) {
        String sql = "SELECT c.courseID, c.courseName, t.teacherID, u.id, u.name, u.username, u.password " +
                "FROM Courses c " +
                "JOIN Teachers t ON c.teacherID = t.id " +
                "JOIN Users u ON u.id = t.id " +
                "WHERE c.courseID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getInt("id"),
                        rs.getInt("teacherID"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password")
                );

                return new Course(rs.getInt("courseID"), rs.getString("courseName"), teacher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT courseID, courseName FROM Courses";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("courseID");
                String name = rs.getString("courseName");
                courses.add(new Course(id, name, null)); // öğretmen null olabilir
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Course> getAllCoursesWithTeachers() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.courseID, c.courseName, u.name AS teacherName " +
                "FROM Courses c " +
                "JOIN Teachers t ON c.teacherID = t.id " +
                "JOIN Users u ON u.id = t.id";


        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int courseID = rs.getInt("courseID");
                String courseName = rs.getString("courseName");
                String teacherName = rs.getString("teacherName");

                Teacher teacher = new Teacher(0, 0, teacherName, "", "");
                courses.add(new Course(courseID, courseName, teacher));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public boolean createCourse(String courseName, int teacherId) {
        String sql = "INSERT INTO Courses (courseName, teacherID) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, courseName);
            stmt.setInt(2, teacherId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<String> getEnrolledStudentsByCourseId(int courseId) {
        List<String> studentNames = new ArrayList<>();

        String sql = "SELECT u.name " +
                "FROM Course_Student cs " +
                "JOIN Students s ON cs.studentID = s.id " +   // 🔥 düzeltilmiş
                "JOIN Users u ON s.id = u.id " +
                "WHERE cs.courseID = ?";


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                studentNames.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentNames;
    }

    public int countCourses() {
        String sql = "SELECT COUNT(*) FROM Courses";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
