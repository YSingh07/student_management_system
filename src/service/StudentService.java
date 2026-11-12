package service;

import model.Student;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    // =========================================================
    // ADD STUDENT
    // =========================================================
    public int addStudent(Student student) {

        String checkSql =
                "SELECT COUNT(*) FROM students WHERE student_id = ?";

        String insertSql =
                "INSERT INTO students " +
                "(student_id, name, age, gender, course, semester, email, attendance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement checkStmt =
                    con.prepareStatement(checkSql);
            PreparedStatement insertStmt =
                    con.prepareStatement(insertSql)
        ) {

            // Check Student ID
            checkStmt.setInt(1, student.getStudentId());

            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {

                System.out.println(
                        "Student ID already exists."
                );

                return -1;
            }

            // Insert Student
            insertStmt.setInt(1, student.getStudentId());
            insertStmt.setString(2, student.getName());
            insertStmt.setInt(3, student.getAge());
            insertStmt.setString(4, student.getGender());
            insertStmt.setString(5, student.getCourse());
            insertStmt.setInt(6, student.getSemester());
            insertStmt.setString(7, student.getEmail());
            insertStmt.setDouble(8, student.getAttendance());

            insertStmt.executeUpdate();

            System.out.println(
                    "Student added successfully."
            );

            return 1;

        } catch (SQLException e) {

            System.out.println(
                    "Failed to add student."
            );

            e.printStackTrace();

            return 0;
        }
    }


    // =========================================================
    // DISPLAY ALL STUDENTS
    // =========================================================
    public void displayAllStudents() {

        String sql =
                "SELECT * FROM students ORDER BY student_id";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql);
            ResultSet rs =
                    ps.executeQuery()
        ) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                Student student = new Student(

                        rs.getInt("student_id"),

                        rs.getString("name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("course"),

                        rs.getInt("semester"),

                        rs.getString("email"),

                        rs.getDouble("attendance")
                );

                student.displayStudent();

                System.out.println(
                        "----------------------"
                );
            }

            if (!found) {

                System.out.println(
                        "No students found."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database error occurred."
            );

            e.printStackTrace();
        }
    }


    // =========================================================
    // SEARCH STUDENT BY ID
    // =========================================================
    public int SearchStudent(int id) {

        String sql =
                "SELECT * FROM students WHERE student_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                Student student = new Student(

                        rs.getInt("student_id"),

                        rs.getString("name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("course"),

                        rs.getInt("semester"),

                        rs.getString("email"),

                        rs.getDouble("attendance")
                );

                student.displayStudent();

                return 1;

            } else {

                System.out.println(
                        "Student not found."
                );

                return -1;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to search student."
            );

            e.printStackTrace();

            return 0;
        }
    }


    // =========================================================
    // UPDATE STUDENT
    // =========================================================
    public int Update(int id, Student up_student) {

        String sql = """
                UPDATE students
                SET name = ?,
                    age = ?,
                    gender = ?,
                    course = ?,
                    semester = ?,
                    email = ?,
                    attendance = ?
                WHERE student_id = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    up_student.getName()
            );

            ps.setInt(
                    2,
                    up_student.getAge()
            );

            ps.setString(
                    3,
                    up_student.getGender()
            );

            ps.setString(
                    4,
                    up_student.getCourse()
            );

            ps.setInt(
                    5,
                    up_student.getSemester()
            );

            ps.setString(
                    6,
                    up_student.getEmail()
            );

            ps.setDouble(
                    7,
                    up_student.getAttendance()
            );

            // Student ID
            ps.setInt(
                    8,
                    id
            );

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Updated Successfully....."
                );

                return 1;

            } else {

                System.out.println(
                        "Student not found....."
                );

                return -1;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to update student."
            );

            e.printStackTrace();

            return 0;
        }
    }


    // =========================================================
    // DELETE STUDENT
    // =========================================================
    public int DeleteStudent(int id) {

        String sql =
                "DELETE FROM students WHERE student_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Successfully removed....."
                );

                return 1;

            } else {

                System.out.println(
                        "Student not found....."
                );

                return -1;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to delete student."
            );

            e.printStackTrace();

            return 0;
        }
    }


    // =========================================================
    // GET ALL STUDENTS
    // =========================================================
    public List<Student> getAllStudents() {

        List<Student> students =
                new ArrayList<>();

        String sql =
                "SELECT * FROM students ORDER BY student_id";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql);
            ResultSet rs =
                    ps.executeQuery()
        ) {

            while (rs.next()) {

                Student student = new Student(

                        rs.getInt("student_id"),

                        rs.getString("name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("course"),

                        rs.getInt("semester"),

                        rs.getString("email"),

                        rs.getDouble("attendance")
                );

                students.add(student);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to fetch students."
            );

            e.printStackTrace();
        }

        return students;
    }


    // =========================================================
    // total student
    // =========================================================

public int getTotalStudents() {

    String sql = "SELECT COUNT(*) FROM students";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()
    ) {

        if (rs.next()) {
            return rs.getInt(1);
        }

    } catch (SQLException e) {
        System.out.println("Failed to count students.");
        e.printStackTrace();
    }

    return 0;
}



    // =========================================================
    // GET ONE STUDENT BY ID
    // =========================================================
    public Student getStudentById(int id) {

        String sql =
                "SELECT * FROM students WHERE student_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return new Student(

                        rs.getInt("student_id"),

                        rs.getString("name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("course"),

                        rs.getInt("semester"),

                        rs.getString("email"),

                        rs.getDouble("attendance")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to search student."
            );

            e.printStackTrace();
        }

        return null;
    }
}