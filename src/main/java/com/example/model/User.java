package com.example.model;

import javax.validation.constraints.*;

import com.example.validation.IsInteger;
import com.example.validation.PasswordMatches;

@PasswordMatches
public class User {
    @NotEmpty(message = "Name is required")
    private String name;

    private String role;

    @NotNull(message = "ID is required")
    @IsInteger
    private Integer id;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$",
        message = "Password must be at least 8 characters long and must contain at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=)."
    )
    private String password;

    @NotEmpty(message = "Confirm Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$",
        message = "Confirm Password must be at least 8 characters long and must contain at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=)."
    )
    private String confirmPassword;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", role=" + role + ", id=" + id + ", password=" + password
                + ", confirmPassword=" + confirmPassword + "]";
    }
}
