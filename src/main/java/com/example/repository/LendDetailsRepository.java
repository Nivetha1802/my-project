package com.example.repository;

import com.example.entity.LendDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LendDetailsRepository extends JpaRepository<LendDetails, Integer> {

    @Query(value = "SELECT * FROM lend_details WHERE user_id = :id", nativeQuery = true)
    List<LendDetails> findLendDetailsById(@Param("id") Integer id);
}