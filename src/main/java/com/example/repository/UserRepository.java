package com.example.repository;

import com.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // UserEntity findByRoleAndIdAndPassword(String role, Integer id, String password);
}
