package com.vls.repository;

import com.vls.exception.VLSException;
import com.vls.model.Course;
import com.vls.model.User;
import com.vls.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    public boolean validateUser(User user) throws VLSException {
        String sql = "SELECT * FROM Login WHERE LoginId = ? AND Password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getLoginId());
            pstmt.setString(2, user.getPassword());
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new VLSException("Error validating user", e);
        }
    }

    public List<Course> searchCourses(String searchTerm) throws VLSException {
        String sql = "SELECT * FROM Course WHERE CourseName LIKE ? OR AuthorName LIKE ?";
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchTerm + "%");
            pstmt.setString(2, "%" + searchTerm + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course(
                            rs.getInt("CourseId"),
                            rs.getString("CourseName"),
                            rs.getString("AuthorName"),
                            rs.getInt("DurationHours"),
                            rs.getBoolean("Availability")
                    );
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            throw new VLSException("Error searching courses", e);
        }
        return courses;
    }
    public List<Course> getAllCourses() throws VLSException {
        String sql = "SELECT * FROM Course";
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("CourseId"),
                        rs.getString("CourseName"),
                        rs.getString("AuthorName"),
                        rs.getInt("DurationHours"),
                        rs.getBoolean("Availability")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new VLSException("Error retrieving all courses", e);
        }
        return courses;
    }

    public Course getCourseById(int courseId) throws VLSException {
        String sql = "SELECT * FROM Course WHERE CourseId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getInt("CourseId"),
                            rs.getString("CourseName"),
                            rs.getString("AuthorName"),
                            rs.getInt("DurationHours"),
                            rs.getBoolean("Availability")
                    );
                }
            }
        } catch (SQLException e) {
            throw new VLSException("Error getting course by ID", e);
        }
        return null;
    }

    public void addCourse(Course course) throws VLSException {
        String sql = "INSERT INTO Course (CourseName, AuthorName, DurationHours, Availability) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getAuthorName());
            pstmt.setInt(3, course.getDurationHours());
            pstmt.setBoolean(4, course.isAvailability());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setCourseId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new VLSException("Error adding course", e);
        }
    }

    public void updateCourse(Course course) throws VLSException {
        String sql = "UPDATE Course SET CourseName = ?, AuthorName = ?, DurationHours = ?, Availability = ? WHERE CourseId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getAuthorName());
            pstmt.setInt(3, course.getDurationHours());
            pstmt.setBoolean(4, course.isAvailability());
            pstmt.setInt(5, course.getCourseId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new VLSException("Error updating course", e);
        }
    }

    public void deleteCourse(int courseId) throws VLSException {
        String sql = "DELETE FROM Course WHERE CourseId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new VLSException("Error deleting course", e);
        }
    }
}