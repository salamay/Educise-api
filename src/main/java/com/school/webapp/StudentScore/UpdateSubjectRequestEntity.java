package com.school.webapp.StudentScore;

public class UpdateSubjectRequestEntity {
    private String id;
    private String table;
    private String subject;

    public UpdateSubjectRequestEntity() {
    }

    public UpdateSubjectRequestEntity(String name, String table, String subject, String oldsubject, String term) {
        this.id = id;
        this.table = table;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
