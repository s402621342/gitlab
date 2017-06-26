package com.example.song4.gitlab.VO;

import java.util.List;

/**
 * Created by song4 on 2017/6/26.
 */

public class AnalysisVO {
    private String questionId;
    private String questionTitle;
    private String score_git_url;
    private String score_score;
    private String score_scored;
    private String test_git_url;
    private String test_compile_succeeded;
    private String test_tested;
    private List<TestcaseVO> testcaseVOList;
    private String metric_git_url;
    private String metric_measured;
    private String metric_total_line_count;
    private String metric_comment_line_count;
    private String metric_field_count;
    private String metric_method_count;
    private String metric_max_coc;

    public AnalysisVO(String questionId, String questionTitle, String score_git_url, String score_score, String score_scored, String test_git_url, String test_compile_succeeded, String test_tested, List<TestcaseVO> testcaseVOList, String metric_git_url, String metric_measured, String metric_total_line_count, String metric_comment_line_count, String metric_field_count, String metric_method_count, String metric_max_coc) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.score_git_url = score_git_url;
        this.score_score = score_score;
        this.score_scored = score_scored;
        this.test_git_url = test_git_url;
        this.test_compile_succeeded = test_compile_succeeded;
        this.test_tested = test_tested;
        this.testcaseVOList = testcaseVOList;
        this.metric_git_url = metric_git_url;
        this.metric_measured = metric_measured;
        this.metric_total_line_count = metric_total_line_count;
        this.metric_comment_line_count = metric_comment_line_count;
        this.metric_field_count = metric_field_count;
        this.metric_method_count = metric_method_count;
        this.metric_max_coc = metric_max_coc;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getScore_git_url() {
        return score_git_url;
    }

    public void setScore_git_url(String score_git_url) {
        this.score_git_url = score_git_url;
    }

    public String getScore_score() {
        return score_score;
    }

    public void setScore_score(String score_score) {
        this.score_score = score_score;
    }

    public String getScore_scored() {
        return score_scored;
    }

    public void setScore_scored(String score_scored) {
        this.score_scored = score_scored;
    }

    public String getTest_git_url() {
        return test_git_url;
    }

    public void setTest_git_url(String test_git_url) {
        this.test_git_url = test_git_url;
    }

    public String getTest_compile_succeeded() {
        return test_compile_succeeded;
    }

    public void setTest_compile_succeeded(String test_compile_succeeded) {
        this.test_compile_succeeded = test_compile_succeeded;
    }

    public String getTest_tested() {
        return test_tested;
    }

    public void setTest_tested(String test_tested) {
        this.test_tested = test_tested;
    }

    public List<TestcaseVO> getTestcaseVOList() {
        return testcaseVOList;
    }

    public void setTestcaseVOList(List<TestcaseVO> testcaseVOList) {
        this.testcaseVOList = testcaseVOList;
    }

    public String getMetric_git_url() {
        return metric_git_url;
    }

    public void setMetric_git_url(String metric_git_url) {
        this.metric_git_url = metric_git_url;
    }

    public String getMetric_measured() {
        return metric_measured;
    }

    public void setMetric_measured(String metric_measured) {
        this.metric_measured = metric_measured;
    }

    public String getMetric_total_line_count() {
        return metric_total_line_count;
    }

    public void setMetric_total_line_count(String metric_total_line_count) {
        this.metric_total_line_count = metric_total_line_count;
    }

    public String getMetric_comment_line_count() {
        return metric_comment_line_count;
    }

    public void setMetric_comment_line_count(String metric_comment_line_count) {
        this.metric_comment_line_count = metric_comment_line_count;
    }

    public String getMetric_field_count() {
        return metric_field_count;
    }

    public void setMetric_field_count(String metric_field_count) {
        this.metric_field_count = metric_field_count;
    }

    public String getMetric_method_count() {
        return metric_method_count;
    }

    public void setMetric_method_count(String metric_method_count) {
        this.metric_method_count = metric_method_count;
    }

    public String getMetric_max_coc() {
        return metric_max_coc;
    }

    public void setMetric_max_coc(String metric_max_coc) {
        this.metric_max_coc = metric_max_coc;
    }
}
