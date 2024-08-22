package com.library.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity<Integer> {

    
    private String name;
    private String role;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<LendDetails> lendDetails;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<LendDetails> getLendDetails() {
        return lendDetails;
    }

    public void setLendDetails(List<LendDetails> lendDetails) {
        this.lendDetails = lendDetails;
    }
}
