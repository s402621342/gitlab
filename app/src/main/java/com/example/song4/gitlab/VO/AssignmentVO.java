package com.example.song4.gitlab.VO;

import java.util.List;

/**
 * Created by song4 on 2017/6/24.
 */

public class AssignmentVO {
    private String id;
    private String title;
    private String description;
    private String type;
    private List<SimpleStudentVO> studentVOList;

    public AssignmentVO(String id, String title, String description, String type, List<SimpleStudentVO> studentVOList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.studentVOList = studentVOList;
    }

    public List<SimpleStudentVO> getStudentVOList() {
        return studentVOList;
    }

    public void setStudentVOList(List<SimpleStudentVO> studentVOList) {
        this.studentVOList = studentVOList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
