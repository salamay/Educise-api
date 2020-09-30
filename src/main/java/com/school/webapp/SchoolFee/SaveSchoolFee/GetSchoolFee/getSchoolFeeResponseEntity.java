package com.school.webapp.SchoolFee.SaveSchoolFee.GetSchoolFee;

import net.sf.jasperreports.engine.JasperPrint;

public class getSchoolFeeResponseEntity {
    private String studentname;
    private String depositorname;
    private String clas;
    private String term;
    private String year;
    private String modeofpayment;
    private String date;
    private String id;
    private int amount;
    private String tag;
    private byte[] pdf;

    public getSchoolFeeResponseEntity() {
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getDepositorname() {
        return depositorname;
    }

    public void setDepositorname(String depositorname) {
        this.depositorname = depositorname;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getModeofpayment() {
        return modeofpayment;
    }

    public void setModeofpayment(String modeofpayment) {
        this.modeofpayment = modeofpayment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
}
