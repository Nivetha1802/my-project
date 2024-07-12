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

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity updateUser(Integer id, UserEntity userDetails) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDetails.getName());
            user.setRole(userDetails.getRole());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // public UserEntity getUserByRoleAndIdAndPassword(String role, Integer id, String password) {
    //     return userRepository.findByRoleAndIdAndPassword(role, id, password);
    // }

    public UserEntity saveUser(User user) {
        UserEntity userEntity = convertToEntity(user);
        return userRepository.save(userEntity);
    }
    private UserEntity convertToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        return userEntity;
    }
}

