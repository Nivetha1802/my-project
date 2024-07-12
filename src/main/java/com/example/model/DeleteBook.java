package com.example.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class DeleteBook {
    

   @NotNull(message = "Book Id is required")
    @Digits(integer = 10, fraction = 0, message = "Book ID must be a number")
    private Integer bookid;

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    @Override
    public String toString() {
        return "DeleteBook [bookid=" + bookid + "]";
    }
    
}
