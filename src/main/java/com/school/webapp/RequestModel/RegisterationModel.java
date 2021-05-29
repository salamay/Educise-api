package com.school.webapp.RequestModel;

public class RegisterationModel {
    private String email;
    private String staffid;
    private String password;
    private String schoolid;
    private String numberofstudent;

    public RegisterationModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getNumberofstudent() {
        return numberofstudent;
    }

    public void setNumberofstudent(String numberofstudent) {
        this.numberofstudent = numberofstudent;
    }
}
