package com.library.model;

import javax.validation.constraints.NotNull;

public class Search {

    @NotNull(message = "Book Id is required")
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
