package com.example.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Lend {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lendId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    private String id;
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
    private String bookname;
    private List<String> authors;
    private LocalDate lendDate;
    private LocalDate returnDate;
    private LocalDate renewDate;
    private Integer renewCount;
    private Double fine;
    

    public String getBookname() {
        return bookname;
    }
    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
    public List<String> getAuthors() {
        return authors;
    }
    public void setAuthors(List<String> authors) {
        this.authors = authors;
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
   
  
    
    
}
