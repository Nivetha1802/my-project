package com.example;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class User {
    @NotEmpty(message = "Name is required")
    private String name;
    private String role;
    @NotEmpty(message = "ID is required")
    private Integer id;
    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotEmpty(message = "Confirm Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String confirm_passsword;
    
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
    public String getConfirm_passsword() {
        return confirm_passsword;
    }
    public void setConfirm_passsword(String confirm_passsword) {
        this.confirm_passsword = confirm_passsword;
    }
    @Override
    public String toString() {
        return "User [name=" + name + ", role=" + role + ", id=" + id + ", password=" + password
                + ", confirm_passsword=" + confirm_passsword + "]";
    }
    
}
