package com.school.webapp.Repository;

public class BookHistory {

    private int id;
    private String title;
    private String author;
    private String amountsold;
    private String buyer;
    private String date;
    private String session;
    private int term;
    private byte[] pdfdocumentbytes;

    public BookHistory() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAmountsold() {
        return amountsold;
    }

    public void setAmountsold(String amountsold) {
        this.amountsold = amountsold;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public byte[] getPdfdocumentbytes() {
        return pdfdocumentbytes;
    }

    public void setPdfdocumentbytes(byte[] pdfdocumentbytes) {
        this.pdfdocumentbytes = pdfdocumentbytes;
    }
}
