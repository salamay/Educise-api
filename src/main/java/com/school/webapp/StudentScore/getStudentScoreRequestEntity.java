package com.school.webapp.StudentScore;


public class getStudentScoreRequestEntity {
    private String table;
    private String name;
    private String term;
    public getStudentScoreRequestEntity() {
    }

    public getStudentScoreRequestEntity(String table, String name, String term) {
        this.table = table;
        this.name = name;
        this.term = term;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
