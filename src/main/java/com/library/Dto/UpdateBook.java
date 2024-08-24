package com.library.Dto;

import javax.validation.constraints.NotEmpty;

public class UpdateBook extends BaseDto<Integer>{

    @NotEmpty(message = "Book name is required")
    private String bookName;

    @NotEmpty(message = "Author Name is required")
    private String author;

    @NotEmpty(message = "Subject is required")
    private String subject;

    @NotEmpty(message = "Info is required")
    private String info;

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
        return "UpdateBook [bookid=" + id + ", bookName=" + bookName + ", subject=" + subject + ", info=" + info
                + "]";
    }

}
