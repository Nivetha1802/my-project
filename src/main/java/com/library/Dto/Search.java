package com.library.Dto;

import javax.validation.constraints.NotNull;
import com.library.validation.IsInteger;

public class Search {

    @NotNull(message = "Book Id is required")
    @IsInteger
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
