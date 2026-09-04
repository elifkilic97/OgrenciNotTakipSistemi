package com.notTakipSistemi.dao;

import com.notTakipSistemi.model.Course;
import com.notTakipSistemi.model.Student;
import com.notTakipSistemi.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDAO {
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public Student createStudent(String name, String username, String password) {
        // Önce kullanıcı adı zaten var mı kontrol et
        if (isUsernameExists(username)) {
            System.out.println("Hata: '" + username + "' kullanıcı adı zaten kullanılıyor!");
            return null;
        }

        String sql = "INSERT INTO Users (name, username, password, userType) VALUES (?, ?, ?, 'Student')";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Yeni öğrenci eklenmişse, id'sini çek
                sql = "SELECT id FROM Users WHERE username = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, username);
                    ResultSet resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");

                        // getNextStudentID ile öğrenci ID'sini hesapla
                        int newStudentID = getNextStudentID();  // Bu metot ile studentID hesaplanacak

                        // Students tablosuna ekle
                        sql = "INSERT INTO Students (id, studentID) VALUES (?, ?)";
                        try (PreparedStatement stmt2 = connection.prepareStatement(sql)) {
                            stmt2.setInt(1, id);
                            stmt2.setInt(2, newStudentID);  // Burada yeni hesaplanan studentID ekleniyor
                            stmt2.executeUpdate();

                            // Yeni oluşturulan öğrenci nesnesi ile geri dönüyoruz
                            return new Student(id, name, username, password, newStudentID);  // Student nesnesi döndürüyoruz
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Eğer bir hata oluşursa veya işlem başarısız olursa null döndürülür
    }




    private int getNextStudentID() {
        String sql = "SELECT MAX(studentID) AS maxID FROM Students";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                int maxID = resultSet.getInt("maxID");
                return maxID + 1; // En büyük olanın üstüne 1 ekle
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1000; // Eğer hiç öğrenci yoksa 1000'den başlasın
    }


    // Kullanıcı adı var mı kontrolü
    private boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Eğer bir sonuç varsa, username zaten var demektir
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Öğrenci bilgilerini ID ile arama
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                return new Student(id, name, username, password, id);  // studentID de id'yle aynı artık değil
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Öğrenci güncelleme (örneğin, şifre güncelleme)
    public boolean updateStudent(int id, String name, String username, String password) {
        String sql = "UPDATE Users SET name = ?, username = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setInt(4, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Öğrenci silme
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM Students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Student getStudentByUsernameAndPassword(String username, String password) {
        Student student = null;
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ? AND userType = 'student'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                student = new Student(id, name, username, password, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT u.id, u.name, u.username, u.password, s.studentID " +
                "FROM Users u JOIN Students s ON u.id = s.id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int studentID = rs.getInt("studentID");

                students.add(new Student(id, name, username, password, studentID));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Course> getCoursesByStudentId(int studentId) {
        List<Course> courses = new ArrayList<>();

        String sql = "SELECT c.courseID, c.courseName, u.name AS teacherName " +
                "FROM Course_Student cs " +
                "JOIN Courses c ON cs.courseID = c.courseID " +
                "JOIN Teachers t ON c.teacherID = t.id " +
                "JOIN Users u ON t.id = u.id " +
                "WHERE cs.studentID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int courseID = rs.getInt("courseID");
                String courseName = rs.getString("courseName");
                String teacherName = rs.getString("teacherName");

                Teacher teacher = new Teacher(0, 0, teacherName, "", "");
                Course course = new Course(courseID, courseName, teacher);
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public int countStudents() {
        String sql = "SELECT COUNT(*) FROM Students";
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
