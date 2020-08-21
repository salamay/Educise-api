package com.school.webapp.Session;

public class SessionRequestEntity {
    private String jss1;
    private String jss2;
    private String jss3;
    private String ss1;
    private String ss2;
    private String ss3;

    public SessionRequestEntity() {
    }

    public SessionRequestEntity(String jss1, String jss2, String jss3, String ss1, String ss2, String ss3) {
        this.jss1 = jss1;
        this.jss2 = jss2;
        this.jss3 = jss3;
        this.ss1 = ss1;
        this.ss2 = ss2;
        this.ss3 = ss3;
    }

    public String getJss1() {
        return jss1;
    }

    public void setJss1(String jss1) {
        this.jss1 = jss1;
    }

    public String getJss2() {
        return jss2;
    }

    public void setJss2(String jss2) {
        this.jss2 = jss2;
    }

    public String getJss3() {
        return jss3;
    }

    public void setJss3(String jss3) {
        this.jss3 = jss3;
    }

    public String getSs1() {
        return ss1;
    }

    public void setSs1(String ss1) {
        this.ss1 = ss1;
    }

    public String getSs2() {
        return ss2;
    }

    public void setSs2(String ss2) {
        this.ss2 = ss2;
    }

    public String getSs3() {
        return ss3;
    }

    public void setSs3(String ss3) {
        this.ss3 = ss3;
    }
}
