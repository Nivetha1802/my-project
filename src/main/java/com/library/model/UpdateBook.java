package com.library.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.library.validation.IsInteger;

public class UpdateBook {

    @NotNull(message = "Book Id is required")
    @IsInteger
    private Integer bookid;

    @NotEmpty(message = "Book name is required")
    private String bookName;

    @NotEmpty(message = "Author Name is required")
    private String author;

    @NotEmpty(message = "Subject is required")
    private String subject;

    @NotEmpty(message = "Info is required")
    private String info;

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "UpdateBook [bookid=" + bookid + ", bookName=" + bookName + ", subject=" + subject + ", info=" + info
                + "]";
    }

}
