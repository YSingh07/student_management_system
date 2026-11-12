package model;

public class Student {

    private int studentId;
    private String name;
    private int age;
    private String gender;
    private String course;
    private int semester;
    private String email;
    private double attendance;

    // Constructor
    public Student(int studentId, String name, int age,
                   String gender, String course,
                   int semester, String email,
                   double attendance) {

        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.course = course;
        this.semester = semester;
        this.email = email;
        this.attendance = attendance;
    }

    // Getters
    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getCourse() {
        return course;
    }

    public int getSemester() {
        return semester;
    }

    public String getEmail() {
        return email;
    }

    public double getAttendance() {
        return attendance;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    // Display Student
    public void displayStudent() {

        System.out.println("ID         : " + studentId);
        System.out.println("Name       : " + name);
        System.out.println("Age        : " + age);
        System.out.println("Gender     : " + gender);
        System.out.println("Course     : " + course);
        System.out.println("Semester   : " + semester);
        System.out.println("Email      : " + email);
        System.out.println("Attendance : " + attendance);
    }
}