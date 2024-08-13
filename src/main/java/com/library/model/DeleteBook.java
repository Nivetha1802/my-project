package com.library.model;

import javax.validation.constraints.NotNull;
import com.library.validation.IsInteger;

public class DeleteBook {

    @NotNull(message = "Book Id is required")
    @IsInteger
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
