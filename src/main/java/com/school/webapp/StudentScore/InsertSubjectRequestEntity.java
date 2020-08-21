package com.school.webapp.StudentScore;

public class InsertSubjectRequestEntity {
    private String name;
    private String table;
    private String subject;

    public InsertSubjectRequestEntity() {
    }

    public InsertSubjectRequestEntity(String name, String table, String subject) {
        this.name = name;
        this.table = table;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
