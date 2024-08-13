package com.library.service;

import java.util.List;
import java.util.Optional;
import com.library.entity.LendDetails;
import com.library.entity.UserEntity;

public interface LendDetailsService extends BaseService<LendDetails, Integer> {

    // List<LendDetails> getAllLendDetails();
    Optional<LendDetails> getLendDetailsById(Integer lendId);
    // LendDetails addLendDetails(LendDetails lendDetails);
    LendDetails updateLendDetails(Integer lendId, LendDetails lendDetails);
    // void deleteLendDetails(Integer lendId);
    void processBookLendDetails(LendDetails book, Optional<UserEntity> user);
    List<LendDetails> getLendDetailsByUserId(Integer id);
    void renewBook(Integer lendId);
   
}

