package com.example.repository;

import com.example.entity.LendDetails;

// import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LendDetailsRepository extends JpaRepository<LendDetails, Integer> {

    // void updateReturnAndRenewDateAndCount(Long lendId, Date returnDate, Date renewDate, Integer renewCount);
}
