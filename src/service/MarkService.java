package service;

import model.Mark;
import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkService {

    // =========================
    // ADD MARK
    // =========================
    public void addMark(Mark mark) {

        String sql = """
                INSERT INTO marks (student_id, subject_id, marks)
                VALUES (?, ?, ?)
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, mark.getStudentId());
            ps.setLong(2, mark.getSubjectId());
            ps.setInt(3, mark.getMarks());

            ps.executeUpdate();

            System.out.println("Mark added successfully!");

        } catch (SQLException e) {

            System.out.println("Failed to add mark.");
            e.printStackTrace();
        }
    }


    // =========================
    // DISPLAY ALL MARKS
    // =========================
    public List<Mark> getAllMarks() {

        List<Mark> marksList = new ArrayList<>();

        String sql = "SELECT * FROM marks ORDER BY mark_id";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Mark mark = new Mark(
                    rs.getLong("mark_id"),
                    rs.getLong("student_id"),
                    rs.getLong("subject_id"),
                    rs.getInt("marks")
                );

                marksList.add(mark);
            }

        } catch (SQLException e) {

            System.out.println("Failed to fetch marks.");
            e.printStackTrace();
        }

        return marksList;
    }


    // =========================
    // total marks records
    // =========================

public int getMarkCount() {
    int count = 0;

    String sql = "SELECT COUNT(*) FROM marks";

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
    // SEARCH MARK BY ID
    // =========================
    public Mark getMarkById(long id) {

        String sql = "SELECT * FROM marks WHERE mark_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Mark(
                    rs.getLong("mark_id"),
                    rs.getLong("student_id"),
                    rs.getLong("subject_id"),
                    rs.getInt("marks")
                );
            }

        } catch (SQLException e) {

            System.out.println("Failed to search mark.");
            e.printStackTrace();
        }

        return null;
    }


    // =========================
    // UPDATE MARK
    // =========================
    public int updateMark(long markId, int marks) {

    String sql = """
        UPDATE marks
        SET marks = ?
        WHERE mark_id = ?
    """;

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, marks);
        ps.setLong(2, markId);

        return ps.executeUpdate();

    } catch (Exception e) {

        e.printStackTrace();
        return 0;
    }
}


    // =========================
    // DELETE MARK
    // =========================
    public void deleteMark(long id) {

        String sql = "DELETE FROM marks WHERE mark_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Mark deleted successfully!");
            } else {
                System.out.println("Mark not found.");
            }

        } catch (SQLException e) {

            System.out.println("Failed to delete mark.");
            e.printStackTrace();
        }
    }

    // =========================
    // SEARCH BY STUDENT ID
    // =========================

public List<Mark> getMarksByStudentId(long studentId) {

    List<Mark> marksList = new ArrayList<>();

    String sql = """
            SELECT *
            FROM marks
            WHERE student_id = ?
            ORDER BY mark_id
            """;

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setLong(1, studentId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Mark mark = new Mark(
                rs.getLong("mark_id"),
                rs.getLong("student_id"),
                rs.getLong("subject_id"),
                rs.getInt("marks")
            );

            marksList.add(mark);
        }

    } catch (SQLException e) {

        System.out.println(
            "Failed to search marks by student ID."
        );

        e.printStackTrace();
    }

    return marksList;
}

    // =========================
    // SEARCH BY SUBJECT ID
    // =========================

public List<Mark> getMarksBySubjectId(long subjectId) {

    List<Mark> marksList = new ArrayList<>();

    String sql = """
            SELECT *
            FROM marks
            WHERE subject_id = ?
            ORDER BY mark_id
            """;

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setLong(1, subjectId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Mark mark = new Mark(
                rs.getLong("mark_id"),
                rs.getLong("student_id"),
                rs.getLong("subject_id"),
                rs.getInt("marks")
            );

            marksList.add(mark);
        }

    } catch (SQLException e) {

        System.out.println(
            "Failed to search marks by subject ID."
        );

        e.printStackTrace();
    }

    return marksList;
}


    // =========================
    // SEARCH BY SUBJECT ID
    // =========================

public Mark getMarkByStudentAndSubject(
        long studentId,
        long subjectId) {

    String sql = """
            SELECT *
            FROM marks
            WHERE student_id = ?
              AND subject_id = ?
            """;

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setLong(1, studentId);
        ps.setLong(2, subjectId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            return new Mark(
                rs.getLong("mark_id"),
                rs.getLong("student_id"),
                rs.getLong("subject_id"),
                rs.getInt("marks")
            );
        }

    } catch (SQLException e) {

        System.out.println(
            "Failed to search mark."
        );

        e.printStackTrace();
    }

    return null;
}
}