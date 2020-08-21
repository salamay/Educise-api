package com.school.webapp.StudentScore;

public class UpdateScoreRequestEntity {
    private String table;
    private String name;
    private String score;
    private String subject;
    private String ca;

    public UpdateScoreRequestEntity() {
    }

    public UpdateScoreRequestEntity(String table, String name, String score, String subject, String ca) {
        this.table = table;
        this.name = name;
        this.score = score;
        this.subject = subject;
        this.ca = ca;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }
}
