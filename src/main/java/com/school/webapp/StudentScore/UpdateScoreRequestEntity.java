package com.school.webapp.StudentScore;

public class UpdateScoreRequestEntity {
    //table corrspond to a table in database
    private String table;
    private String id;
    //Score is the value to store
    private String score;
    //ca is the column name
    private String ca;

    public UpdateScoreRequestEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

}
