package com.example;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;

public class Student {
    @Size(min = 3, max = 15, message = "Name length should be in between 3 and 15")
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private char[] password;
    private String branch;
    @NotEmpty(message = "Please Choose one Option")
    private String gender;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthDate;
    private String[] hobbies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password=" + Arrays.toString(password) +
                ", branch='" + branch + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", hobbies=" + Arrays.toString(hobbies) +
                '}';
    }
}