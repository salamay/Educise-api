package com.school.webapp.Information.StudentandParent;

public class StudentInformationRequestEntity {
    private String name;
    private String clas;

    public StudentInformationRequestEntity() {
    }

    public StudentInformationRequestEntity(String name, String clas) {
        this.name = name;
        this.clas = clas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }
}
