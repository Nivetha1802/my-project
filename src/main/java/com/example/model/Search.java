package com.example.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class Search {

    @NotNull(message = "Book Id is required")
    @Digits(integer = 10, fraction = 0, message = "Book ID must be a number")
    private Integer query;

    public Integer getQuery() {
        return query;
    }

    public void setQuery(Integer query) {
        this.query = query;
    }
}
