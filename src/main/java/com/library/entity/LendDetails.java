package com.library.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class LendDetails extends BaseEntity<Integer> {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    private String title;
    private String authors;
    private LocalDate lendDate;
    private LocalDate returnDate;
    private Integer renewCount;
    private Double fine;
    private String bookid;
    

    // Constructors, Getters, Setters, and Methods
    public LendDetails( Integer id, String title, String authors, LocalDate lendDate, LocalDate returnDate) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }

    public LendDetails() {
    }
    
    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

 

    public void calculateFine(LocalDate actualReturnDate) {
        if (this.returnDate != null && actualReturnDate.isAfter(returnDate)) {
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
