package com.school.webapp.WebAppService.StudentScore;

public class UpdateSubjectRequestEntity {
    private String id;
    private String subject;

    public UpdateSubjectRequestEntity() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
