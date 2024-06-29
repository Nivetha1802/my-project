package com.example;

public class Lend {
    private String book_name;
    private int id;
    private String date_of_lending;
    private String date_of_return;
    private int fine_amount_left;

    // Getters and Setters
    public String getBook_name() {
        return book_name;
    }
    public void setBook_name(String book_name) {
        this.book_name = book_name;
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
}
