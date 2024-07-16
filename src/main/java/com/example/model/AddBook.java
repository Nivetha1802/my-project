package com.example.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddBook {

    @NotNull(message = "Book Id is required")
    @Digits(integer = 10, fraction = 0, message = "Book ID must be a number")
    private Integer bookid;

    @NotEmpty(message = "Book name is required")
    private String bookname;
    
    @NotEmpty(message = "Author name is required")
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
        return "AddBook [bookid=" + bookid + ", bookName=" + bookname +",author=" + author + ", subject=" + subject + ", info=" + info + "]";
    }

}
