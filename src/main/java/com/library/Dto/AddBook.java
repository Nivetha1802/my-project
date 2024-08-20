package com.library.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.library.validation.IsInteger;

public class AddBook {

    @NotNull(message = "Book Id is required")
    @IsInteger
    private Integer bookid;

    @NotEmpty(message = "Book name is required")
    private String bookname;

    @NotEmpty(message = "Author name is required")
    private String author;

    @NotEmpty(message = "Subject is required")
    private String subject;

    @NotEmpty(message = "Info is required")
    private String info;

    @NotNull(message = "Book Id is required")
    @IsInteger
    private Integer bookcount;

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Integer getBookcount() {
        return bookcount;
    }

    public void setBookcount(Integer bookcount) {
        this.bookcount = bookcount;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookname;
    }

    public void setBookName(String bookName) {
        this.bookname = bookName;
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
        return "AddBook [bookid=" + bookid + ", bookname=" + bookname + ", author=" + author + ", subject=" + subject
                + ", info=" + info + ", bookcount=" + bookcount + "]";
    }


}
