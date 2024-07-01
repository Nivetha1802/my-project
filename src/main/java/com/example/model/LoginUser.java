package com.example.model;

public class LoginUser {
    private String role;
    private int id;
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