package model;

public class Mark {

    private long markId;
    private long studentId;
    private long subjectId;
    private int marks;

    // Constructor
    public Mark(long markId, long studentId, long subjectId, int marks) {
        this.markId = markId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.marks = marks;
    }

    // Getter
    public long getMarkId() {
        return markId;
    }

    // Setter
    public void setMarkId(long markId) {
        this.markId = markId;
    }

    // Getter
    public long getStudentId() {
        return studentId;
    }

    // Setter
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    // Getter
    public long getSubjectId() {
        return subjectId;
    }

    // Setter
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    // Getter
    public int getMarks() {
        return marks;
    }

    // Setter
    public void setMarks(int marks) {
        this.marks = marks;
    }
}