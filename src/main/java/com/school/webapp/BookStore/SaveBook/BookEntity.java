package com.school.webapp.BookStore.SaveBook;

import javax.persistence.*;

@Entity
@Table(name = "book_entity")
public class BookEntity {
    @Id
    private String id;
    private String schoolid;
    private String title;
    private String author;
    private int price;
    private int copies;
    private String year;
    private String term;
    //date is marked as transient because it is not included when saving book but when saving to history
    @Transient
    private String date;

    public BookEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolid() {
        return schoolid;
    }



    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
