package com.library.service;

import java.util.Optional;

import com.library.Dto.User;
import com.library.entity.UserEntity;

public interface UserService extends BaseService<UserEntity, Integer> {
   
   void saveUser(User user);
   Optional<UserEntity> authenticate(Integer id, String password);

}

