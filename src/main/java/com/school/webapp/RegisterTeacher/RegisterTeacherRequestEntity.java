package com.school.webapp.RegisterTeacher;

public class RegisterTeacherRequestEntity {
   private String firstname;
    private String lastname;
    private String middlename;
    private String clas;
    private String subjectone;
    private String subjecttwo;
    private String subjectthree;
    private String subjectfour;
    private String email;
    private String address;
    private String entryyear;
    private String gender;
    private String maritalstatus;
    private String phoneno;
    private String schoolattended;
    private String course;
    private byte[] file;

    public RegisterTeacherRequestEntity() {
    }

    public RegisterTeacherRequestEntity(String firstname, String lastname, String middlename, String clas, String subjectone, String subjecttwo, String subjectthree, String subjectfour, String email, String address, String entryyear, String gender, String maritalstatus, String phoneno, String schoolattended, String course) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.clas = clas;
        this.subjectone = subjectone;
        this.subjecttwo = subjecttwo;
        this.subjectthree = subjectthree;
        this.subjectfour = subjectfour;
        this.email = email;
        this.address = address;
        this.entryyear = entryyear;
        this.gender = gender;
        this.maritalstatus = maritalstatus;
        this.phoneno = phoneno;
        this.schoolattended = schoolattended;
        this.course = course;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getSubjectone() {
        return subjectone;
    }

    public void setSubjectone(String subjectone) {
        this.subjectone = subjectone;
    }

    public String getSubjecttwo() {
        return subjecttwo;
    }

    public void setSubjecttwo(String subjecttwo) {
        this.subjecttwo = subjecttwo;
    }

    public String getSubjectthree() {
        return subjectthree;
    }

    public void setSubjectthree(String subjectthree) {
        this.subjectthree = subjectthree;
    }

    public String getSubjectfour() {
        return subjectfour;
    }

    public void setSubjectfour(String subjectfour) {
        this.subjectfour = subjectfour;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEntryyear() {
        return entryyear;
    }

    public void setEntryyear(String entryyear) {
        this.entryyear = entryyear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getSchoolattended() {
        return schoolattended;
    }

    public void setSchoolattended(String schoolattended) {
        this.schoolattended = schoolattended;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
