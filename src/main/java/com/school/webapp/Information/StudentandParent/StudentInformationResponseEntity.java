package com.school.webapp.Information.StudentandParent;


public class StudentInformationResponseEntity {
    private int id;
    private String studentname;
    private int age;
    private String fathername;
    private String mothername;
    private String nextofkin;
    private String address;
    private String phoneno;
    private String nickname;
    private String hobbies;
    private String turnon;
    private String turnoff;
    private String club;
    private String rolemodel;
    private String futureambition;
    private String gender;
    private byte[] student;
    private byte[] father;
    private byte[] mother;
    private String clas;
    private String tag;

    public StudentInformationResponseEntity() {
    }

    public StudentInformationResponseEntity(int id, String studentname, int age, String fathername, String mothername, String nextofkin, String address, String phoneno, String nickname, String hobbies, String turnon, String turnoff, String club, String rolemodel, String futureambition, String gender, byte[] student, byte[] father, byte[] mother, String clas, String tag) {
        this.id = id;
        this.studentname = studentname;
        this.age = age;
        this.fathername = fathername;
        this.mothername = mothername;
        this.nextofkin = nextofkin;
        this.address = address;
        this.phoneno = phoneno;
        this.nickname = nickname;
        this.hobbies = hobbies;
        this.turnon = turnon;
        this.turnoff = turnoff;
        this.club = club;
        this.rolemodel = rolemodel;
        this.futureambition = futureambition;
        this.gender = gender;
        this.student = student;
        this.father = father;
        this.mother = mother;
        this.clas = clas;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getNextofkin() {
        return nextofkin;
    }

    public void setNextofkin(String nextofkin) {
        this.nextofkin = nextofkin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getTurnon() {
        return turnon;
    }

    public void setTurnon(String turnon) {
        this.turnon = turnon;
    }

    public String getTurnoff() {
        return turnoff;
    }

    public void setTurnoff(String turnoff) {
        this.turnoff = turnoff;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getRolemodel() {
        return rolemodel;
    }

    public void setRolemodel(String rolemodel) {
        this.rolemodel = rolemodel;
    }

    public String getFutureambition() {
        return futureambition;
    }

    public void setFutureambition(String futureambition) {
        this.futureambition = futureambition;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getStudent() {
        return student;
    }

    public void setStudent(byte[] student) {
        this.student = student;
    }

    public byte[] getFather() {
        return father;
    }

    public void setFather(byte[] father) {
        this.father = father;
    }

    public byte[] getMother() {
        return mother;
    }

    public void setMother(byte[] mother) {
        this.mother = mother;
    }

}
