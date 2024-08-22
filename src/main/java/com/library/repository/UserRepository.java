package com.library.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.library.entity.UserEntity;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Integer> {

    // Optional<UserEntity> findByName(String username);

}
