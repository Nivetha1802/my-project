package com.library.repository;

import org.springframework.stereotype.Repository;
import com.library.entity.UserEntity;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Integer> {

    // UserEntity findByName(String name);

}
