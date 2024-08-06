package com.library.service;

import org.springframework.stereotype.Service;
import com.library.entity.UserEntity;
import com.library.model.User;
import com.library.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    
    
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
   
    @Override
    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setRole(user.getRole().toUpperCase());
        userEntity.setPassword(user.getPassword());
        createUser(userEntity);
    }

    @Override
    public UserEntity authenticate(Integer id, String password) {
        UserEntity user = getUserById(id);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
