// package com.example.entity;

// import javax.persistence.*;
// import java.util.Date;

// @Entity
// @Table(name = "LendDetails")
// public class LendDetails {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long lendId;

//     @ManyToOne
//     @JoinColumn(name = "user", referencedColumnName = "id")
//     private UserEntity user;

//     private String bookname;
//     private Integer bookid;
//     private Date lendDate;
//     private Date returnDate;
//     private Date renewDate;
//     private Integer renewCount;
//     private Double fine;

//     public Long getLendId() {
//         return lendId;
//     }

//     public void setLendId(Long lendId) {
//         this.lendId = lendId;
//     }

//     public UserEntity getUser() {
//         return user;
//     }

//     public void setUser(UserEntity user) {
//         this.user = user;
//     }

//     public String getBookname() {
//         return bookname;
//     }

//     public void setBookname(String bookname) {
//         this.bookname = bookname;
//     }

//     public Integer getBookid() {
//         return bookid;
//     }

//     public void setBookid(Integer bookid) {
//         this.bookid = bookid;
//     }

//     public Date getLendDate() {
//         return lendDate;
//     }

//     public void setLendDate(Date lendDate) {
//         this.lendDate = lendDate;
//     }

//     public Date getReturnDate() {
//         return returnDate;
//     }

//     public void setReturnDate(Date returnDate) {
//         this.returnDate = returnDate;
//     }

//     public Date getRenewDate() {
//         return renewDate;
//     }

//     public void setRenewDate(Date renewDate) {
//         this.renewDate = renewDate;
//     }

//     public Integer getRenewCount() {
//         return renewCount;
//     }

//     public void setRenewCount(Integer renewCount) {
//         this.renewCount = renewCount;
//     }

//     public Double getFine() {
//         return fine;
//     }

//     public void setFine(Double fine) {
//         this.fine = fine;
//     }
// }

