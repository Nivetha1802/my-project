package com.library.model;

import javax.validation.constraints.NotNull;
import com.library.validation.IsInteger;

public class FineDetails {

    @NotNull(message = "ID is required")
    @IsInteger
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
