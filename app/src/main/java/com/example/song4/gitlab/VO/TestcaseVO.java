package com.example.song4.gitlab.VO;

/**
 * Created by song4 on 2017/6/26.
 */

public class TestcaseVO {
    private String name;
    private String passed;

    public TestcaseVO(String name, String passed) {
        this.name = name;
        this.passed = passed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }
}
