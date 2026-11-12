package model;

public class Subject {

    private long subjectId;
    private String subjectName;
    private int semester;
    private String course;

    // Constructor
    public Subject(long subjectId, String subjectName, int semester, String course) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.semester = semester;
        this.course = course;
    }

    // Getter for subjectId
    public long getSubjectId() {
        return subjectId;
    }

    // Setter for subjectId
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    // Getter for subjectName
    public String getSubjectName() {
        return subjectName;
    }

    // Setter for subjectName
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    // Getter for semester
    public int getSemester() {
        return semester;
    }

    // Setter for semester
    public void setSemester(int semester) {
        this.semester = semester;
    }

    // Getter for course
    public String getCourse() {
        return course;
    }

    // Setter for course
    public void setCourse(String course) {
        this.course = course;
    }
}