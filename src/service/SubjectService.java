package service;

import model.Subject;
import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectService {

    // =========================
    // ADD SUBJECT
    // =========================
    public void addSubject(Subject subject) {

        String sql = "INSERT INTO subjects (subject_name, semester, course) VALUES (?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, subject.getSubjectName());
            ps.setInt(2, subject.getSemester());
            ps.setString(3, subject.getCourse());

            ps.executeUpdate();

            System.out.println("Subject added successfully!");

        } catch (SQLException e) {

            System.out.println("Failed to add subject.");
            e.printStackTrace();
        }
    }


    // =========================
    // DISPLAY ALL SUBJECTS
    // =========================
    public List<Subject> getAllSubjects() {

        List<Subject> subjects = new ArrayList<>();

        String sql = "SELECT * FROM subjects ORDER BY subject_id";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Subject subject = new Subject(
                    rs.getLong("subject_id"),
                    rs.getString("subject_name"),
                    rs.getInt("semester"),
                    rs.getString("course")
                );

                subjects.add(subject);
            }

        } catch (SQLException e) {

            System.out.println("Failed to fetch subjects.");
            e.printStackTrace();
        }

        return subjects;
    }

    // =========================
    // total subjects
    // =========================

public int getSubjectCount() {
    int count = 0;

    String sql = "SELECT COUNT(*) FROM subjects";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            count = rs.getInt(1);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return count;
}


    // =========================
    // SEARCH SUBJECT
    // =========================
    public Subject getSubjectById(long id) {

        String sql = "SELECT * FROM subjects WHERE subject_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Subject(
                    rs.getLong("subject_id"),
                    rs.getString("subject_name"),
                    rs.getInt("semester"),
                    rs.getString("course")
                );
            }

        } catch (SQLException e) {

            System.out.println("Failed to search subject.");
            e.printStackTrace();
        }

        return null;
    }


    // =========================
    // UPDATE SUBJECT
    // =========================
    public void updateSubject(long id, Subject subject) {

        String sql = """
                UPDATE subjects
                SET subject_name = ?,
                    semester = ?,
                    course = ?
                WHERE subject_id = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, subject.getSubjectName());
            ps.setInt(2, subject.getSemester());
            ps.setString(3, subject.getCourse());
            ps.setLong(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Subject updated successfully!");
            } else {
                System.out.println("Subject not found.");
            }

        } catch (SQLException e) {

            System.out.println("Failed to update subject.");
            e.printStackTrace();
        }
    }


    // =========================
    // DELETE SUBJECT
    // =========================
    public void deleteSubject(long id) {

        String sql = "DELETE FROM subjects WHERE subject_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Subject deleted successfully!");
            } else {
                System.out.println("Subject not found.");
            }

        } catch (SQLException e) {

            System.out.println("Failed to delete subject.");
            e.printStackTrace();
        }
    }
}