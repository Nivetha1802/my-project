package com.example.model;

import javax.validation.constraints.*;

public class Lend {
    @NotNull(message = "Book Id is required")
    @Digits(integer = 10, fraction = 0, message = "Book ID must be a number")
    private int bookid;

    @NotNull(message = "ID is required")
    @Digits(integer = 10, fraction = 0, message = "ID must be a number")
    private int id;
    
    private String date_of_lending;
    private String date_of_return;
    private int fine_amount_left;

    // Getters and Setters
    public int getBookid() {
        return bookid;
    }
    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDate_of_lending() {
        return date_of_lending;
    }
    public void setDate_of_lending(String date_of_lending) {
        this.date_of_lending = date_of_lending;
    }
    public String getDate_of_return() {
        return date_of_return;
    }
    public void setDate_of_return(String date_of_return) {
        this.date_of_return = date_of_return;
    }
    public int getFine_amount_left() {
        return fine_amount_left;
    }
    public void setFine_amount_left(int fine_amount_left) {
        this.fine_amount_left = fine_amount_left;
    }
    @Override
    public String toString() {
        return "Lend [bookid=" + bookid + ", id=" + id + ", date_of_lending=" + date_of_lending
                + ", date_of_return=" + date_of_return + ", fine_amount_left=" + fine_amount_left + "]";
    }
    public void setAuthor(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAuthor'");
    }
    public void setBookname(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBookname'");
    }
}
