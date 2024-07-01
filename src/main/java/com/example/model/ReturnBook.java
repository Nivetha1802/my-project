package com.example.model;
import java.util.Date;

public class ReturnBook {
    
    private int id;
    private String bookname;
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
    public String getBookname() {
        return bookname;
    }
    public void setBookname(String bookname) {
        this.bookname = bookname;
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
        return "Return [id=" + id + ", bookname=" + bookname + ", date_of_lending=" + date_of_lending
                + ", date_of_return=" + date_of_return + ", actual_date_of_returning=" + actual_date_of_returning
                + ", fine=" + fine + ", fine_amount_left=" + fine_amount_left + "]";
    }
    
}