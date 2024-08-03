package com.example.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class LendDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lendId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    private String id;
    private String title;
    private String authors;
    private LocalDate lendDate;
    private LocalDate returnDate;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthors() {
        return authors;
    }
    public void setAuthors(String authors) {
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
    @Override
    public String toString() {
        return "LendDetails [lendId=" + lendId + ", user=" + user + ", id=" + id + ", title=" + title + ", authors="
                + authors + ", lendDate=" + lendDate + ", returnDate=" + returnDate +
                 ",renewCount=" + renewCount + ", fine=" + fine + "]";
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
