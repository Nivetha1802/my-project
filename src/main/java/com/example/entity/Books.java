package com.example.entity;

import javax.persistence.*;


@Entity
@Table(name = "books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookid;

    private String bookname;
    private String author;
    private String subject;
    private String info;
    @Column(nullable = false, columnDefinition = "int default 10")
    private Integer bookcount = 10;

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

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
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
}

