package com.library.service;

import com.library.entity.UserEntity;
import com.library.model.User;

public interface UserService {

   UserEntity getUserById(Integer id);
   UserEntity createUser(UserEntity user);
   void saveUser(User user);
   UserEntity authenticate(Integer id, String password);

}

