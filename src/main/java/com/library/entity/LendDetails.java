package com.library.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class LendDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lendId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    private String id;
    private String title;
    private String authors;
    private LocalDate lendDate;
    private LocalDate returnDate;
    private Integer renewCount;
    private Double fine;

    // Constructors, Getters, Setters, and Methods
    public LendDetails(String id, String title, String authors, LocalDate lendDate, LocalDate returnDate) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }

    public LendDetails() {
    }

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
