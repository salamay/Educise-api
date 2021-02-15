package com.school.webapp.Repository;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")

public class PaymentEntity {
    @Id
    private int id;
    private String schoolid;
    private String paymentdate;
    private String amount;
    private String expirydate;
    private  String token;

    public PaymentEntity() {
        Date date=new Date();
        int year=date.getYear();
        int month=date.getMonth();
        int day=date.getDay();
        long time=date.getTime();
        paymentdate=String.valueOf(month)+"/"+String.valueOf(day)+"/"+String.valueOf(year)+"/"+String.valueOf(time);
        System.out.println("Payment date: "+paymentdate);
        expirydate=String.valueOf(month+1)+"/"+String.valueOf(day)+"/"+String.valueOf(year)+"/"+String.valueOf(time);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getPaymentdate() {
        return paymentdate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public void setPaymentdate(String paymentdate) {
        this.paymentdate = paymentdate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
