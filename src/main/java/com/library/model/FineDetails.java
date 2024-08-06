package com.library.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class FineDetails {
    
    @NotNull(message = "ID is required")
    @Digits(integer = 10, fraction = 0, message = "ID must be a number")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FineDetails [id=" + id + "]";
    }

    
}
