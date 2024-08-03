package com.example.service;

import com.example.entity.UserEntity;
import com.example.model.User;
import com.example.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public void saveUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setRole(user.getRole());
        userEntity.setPassword(user.getPassword());
        userRepository.save(userEntity);
    }

    // public UserEntity saveUser(User user) {
    //     UserEntity userEntity = convertToEntity(user);
    //     return userRepository.save(userEntity);
    // }
    // private UserEntity convertToEntity(User user) {
    //     UserEntity userEntity = new UserEntity();
    //     userEntity.setId(user.getId());
    //     userEntity.setPassword(user.getPassword());
    //     userEntity.setRole(user.getRole());
    //     userEntity.setName(user.getName());
    //     return userEntity;
    // }

    public UserEntity authenticate(Integer id, String password) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}

