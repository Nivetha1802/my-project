package com.library.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.library.entity.LendDetails;

@Repository
public interface LendDetailsRepository extends BaseRepository<LendDetails, Integer> {

    @Query(value = "SELECT * FROM lend_details WHERE user_id = :id", nativeQuery = true)
    List<LendDetails> findLendDetailsById(@Param("id") Integer id);
}