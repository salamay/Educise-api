package com.school.webapp.StudentScore;


public class getStudentScoreRequestEntity {
    private String table;
    private String name;
    public getStudentScoreRequestEntity() {
    }

    public getStudentScoreRequestEntity(String table, String name) {
        this.table = table;
        this.name = name;
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
}
