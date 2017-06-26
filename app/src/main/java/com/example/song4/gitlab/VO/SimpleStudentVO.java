package com.example.song4.gitlab.VO;

/**
 * Created by song4 on 2017/6/24.
 */

public class SimpleStudentVO {
    private String studentId;
    private String studentName;
    private String studentNumber;
    private String score;
    private String scored;

    public SimpleStudentVO(String studentId, String studentName, String studentNumber, String score, String scored) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.score = score;
        this.scored = scored;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScored() {
        return scored;
    }

    public void setScored(String scored) {
        this.scored = scored;
    }
}
