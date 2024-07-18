package com.example.service;

import java.time.LocalDate;
import com.example.entity.LendDetails;
import com.example.repository.LendDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class LendDetailsService {

    private final LendDetailsRepository lendDetailsRepository;

    
    public LendDetailsService(LendDetailsRepository lendDetailsRepository) {
        this.lendDetailsRepository = lendDetailsRepository;
    }

    @Transactional
    public LendDetails createLendDetails(LendDetails lendDetails) {
        return lendDetailsRepository.save(lendDetails);
    }

    @Transactional
    public void deleteLendDetails(Integer lendId) {
        lendDetailsRepository.deleteById(lendId);
    }

    public List<LendDetails> getAllLendDetails() {
        return lendDetailsRepository.findAll();
    }

  
    public void saveLendedBooks(List<LendDetails> lendedBooks) {
        lendDetailsRepository.saveAll(lendedBooks);
    }

    public List<LendDetails> getLendDetailsByUserId(Integer id) {
        List<LendDetails> lendDetailsList = lendDetailsRepository.findLendDetailsById(id);
        LocalDate actualReturnDate = LocalDate.now();
        for (LendDetails lendDetails : lendDetailsList) {
            lendDetails.calculateFine(actualReturnDate);
        }
        return lendDetailsList;
    }

    @Transactional
    public void renewBook(Integer lendId) {
        LendDetails lendDetails = lendDetailsRepository.findById(lendId).orElseThrow(() -> new IllegalArgumentException("Invalid lend ID"));
        lendDetails.setRenewCount(lendDetails.getRenewCount() + 1);
        lendDetails.setReturnDate(lendDetails.getReturnDate().plusDays(14)); 
        lendDetailsRepository.save(lendDetails);
    }

}

