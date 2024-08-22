package com.library.Dto;

import javax.validation.constraints.*;
import com.library.validation.IsInteger;

public class LoginUser {
    @NotEmpty(message = "Role is required")
    private String role;

    @NotNull(message = "ID is required")
    @IsInteger
    private int id;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$", message = "Password must be at least 8 characters long and must contain at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=).")
    private String password;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginUser [role=" + role + ", id=" + id + ", password=" + password + "]";
    }
}