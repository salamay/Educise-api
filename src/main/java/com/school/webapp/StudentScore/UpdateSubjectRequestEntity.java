package com.school.webapp.StudentScore;

public class UpdateSubjectRequestEntity {
    private String name;
    private String table;
    private String subject;
    private String oldsubject;

    public UpdateSubjectRequestEntity() {
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

    public String getOldsubject() {
        return oldsubject;
    }

    public void setOldsubject(String oldsubject) {
        this.oldsubject = oldsubject;
    }
}
