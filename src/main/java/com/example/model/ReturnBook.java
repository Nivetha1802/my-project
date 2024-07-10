package com.example.model;
import java.util.Date;

import javax.validation.constraints.*;

public class ReturnBook {
    
    @NotNull(message = "Id is required")
    // @Digits(integer = 10, fraction = 0, message = "ID must be a number")
    private int id;

    @NotNull(message = "Book Id is required")
    // @Digits(integer = 10, fraction = 0, message = "Book ID must be a number")    
    private int bookid;
    private Date date_of_lending;
    private Date date_of_return;
    private Date actual_date_of_returning;
    private int fine;
    private int fine_amount_left;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getbookid() {
        return bookid;
    }
    public void setBookid(int bookid) {
        this.bookid = bookid;
    }
    public Date getDate_of_lending() {
        return date_of_lending;
    }
    public void setDate_of_lending(Date date_of_lending) {
        this.date_of_lending = date_of_lending;
    }
    public Date getDate_of_return() {
        return date_of_return;
    }
    public void setDate_of_return(Date date_of_return) {
        this.date_of_return = date_of_return;
    }
    public Date getActual_date_of_returning() {
        return actual_date_of_returning;
    }
    public void setActual_date_of_returning(Date actual_date_of_returning) {
        this.actual_date_of_returning = actual_date_of_returning;
    }
    public int getFine() {
        return fine;
    }
    public void setFine(int fine) {
        this.fine = fine;
    }
    public int getFine_amount_left() {
        return fine_amount_left;
    }
    public void setFine_amount_left(int fine_amount_left) {
        this.fine_amount_left = fine_amount_left;
    }
    @Override
    public String toString() {
        return "Return [id=" + id + ", bookid=" + bookid + ", date_of_lending=" + date_of_lending
                + ", date_of_return=" + date_of_return + ", actual_date_of_returning=" + actual_date_of_returning
                + ", fine=" + fine + ", fine_amount_left=" + fine_amount_left + "]";
    }
    
}