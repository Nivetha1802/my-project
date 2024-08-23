package com.library.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
public class Books extends BaseEntity<Integer> implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    private String bookname;
    private String author;
    private String subject;
    private String info;

    @Column(nullable = false, columnDefinition = "int default 10")
    private Integer bookcount = 10;

    // Constructors, Getters, Setters, and Methods
    public Books(Integer id, String bookname, String author, String subject, String info, Integer bookcount) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.subject = subject;
        this.info = info;
        this.bookcount = bookcount;
    }

    public Books() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookcount() {
        return bookcount;
    }

    public void setBookcount(Integer bookcount) {
        this.bookcount = bookcount;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
