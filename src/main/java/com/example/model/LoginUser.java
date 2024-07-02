package com.example.model;
import javax.validation.constraints.*;
public class LoginUser {
    @NotEmpty(message = "Name is required")
    private String role;

    @NotNull(message = "ID is required")
    @Digits(integer = 10, fraction = 0, message = "ID must be a number")
    private int id;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$",
        message = "Password must be at least 8 characters long and must contain at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=)."
    )
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