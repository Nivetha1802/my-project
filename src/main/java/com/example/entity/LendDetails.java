package com.example.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "LendDetails")
public class LendDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lendId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    private String bookname;
    private Integer bookid;
    private String author;
    private String subject;
    private String info;
    private LocalDate lendDate;
    private LocalDate returnDate;
    private LocalDate renewDate;
    private Integer renewCount;
    private Double fine;

    public Integer getLendId() {
        return lendId;
    }

    public void setLendId(Integer lendId) {
        this.lendId = lendId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public LocalDate getLendDate() {
        return lendDate;
    }

    public void setLendDate(LocalDate lendDate) {
        this.lendDate = lendDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(LocalDate renewDate) {
        this.renewDate = renewDate;
    }

    public Integer getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(Integer renewCount) {
        this.renewCount = renewCount;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
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

    public String getUserName() {
        return user != null ? user.getName() : null;
    }

    public Integer getUserId() {
        return user != null ? user.getId() : null;
    }

    public void calculateFine(LocalDate actualReturnDate) {
        if (actualReturnDate.isAfter(returnDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(returnDate, actualReturnDate);
            this.fine = daysOverdue * 10.0; 
        } else {
            this.fine = 0.0;
        }
    }
    
    public LocalDate calculateExtendedReturnDate() {
        return this.returnDate.plusDays(14); 
    }

}

