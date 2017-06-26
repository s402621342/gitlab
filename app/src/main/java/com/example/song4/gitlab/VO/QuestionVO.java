package com.example.song4.gitlab.VO;

/**
 * Created by song4 on 2017/6/23.
 */

public class QuestionVO {
    private String id;
    private String title;
    private String description;
    private String difficulty;
    private String gitUrl;
    private String type;
    private String creator_id;
    private String creator_username;
    private String creator_name;
    private String creator_type;
    private String creator_avatar;
    private String creator_gender;
    private String creator_email;
    private String creator_schooId;
    private String duration;
    private String link;
    private String knowledgeVos;
    private String readme;


    public QuestionVO(String id, String title, String description, String difficulty, String gitUrl, String type, String creator_id, String creator_username, String creator_name, String creator_type, String creator_avatar, String creator_gender, String creator_email, String creator_schooId, String duration, String link, String knowledgeVos, String readme) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.gitUrl = gitUrl;
        this.type = type;
        this.creator_id = creator_id;
        this.creator_username = creator_username;
        this.creator_name = creator_name;
        this.creator_type = creator_type;
        this.creator_avatar = creator_avatar;
        this.creator_gender = creator_gender;
        this.creator_email = creator_email;
        this.creator_schooId = creator_schooId;
        this.duration = duration;
        this.link = link;
        this.knowledgeVos = knowledgeVos;
        this.readme = readme;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getCreator_username() {
        return creator_username;
    }

    public void setCreator_username(String creator_username) {
        this.creator_username = creator_username;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getCreator_type() {
        return creator_type;
    }

    public void setCreator_type(String creator_type) {
        this.creator_type = creator_type;
    }

    public String getCreator_avatar() {
        return creator_avatar;
    }

    public void setCreator_avatar(String creator_avatar) {
        this.creator_avatar = creator_avatar;
    }

    public String getCreator_gender() {
        return creator_gender;
    }

    public void setCreator_gender(String creator_gender) {
        this.creator_gender = creator_gender;
    }

    public String getCreator_email() {
        return creator_email;
    }

    public void setCreator_email(String creator_email) {
        this.creator_email = creator_email;
    }

    public String getCreator_schooId() {
        return creator_schooId;
    }

    public void setCreator_schooId(String creator_schooId) {
        this.creator_schooId = creator_schooId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKnowledgeVos() {
        return knowledgeVos;
    }

    public void setKnowledgeVos(String knowledgeVos) {
        this.knowledgeVos = knowledgeVos;
    }
}
