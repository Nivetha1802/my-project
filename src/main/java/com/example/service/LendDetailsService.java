package com.example.service;

import com.example.entity.Books;
import com.example.entity.LendDetails;
import com.example.model.Lend;
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

    @Transactional
    public void renewBook(Integer lendId) {
        LendDetails lendDetails = lendDetailsRepository.findById(lendId).orElseThrow(() -> new RuntimeException("LendDetails not found"));
        lendDetails.setRenewCount(lendDetails.getRenewCount() + 1);

        // Extend the return date by 14 days
        // Calendar calendar = Calendar.getInstance();
        // calendar.setTime(lendDetails.getReturnDate());
        // calendar.add(Calendar.DAY_OF_YEAR, 14);
        // lendDetails.setReturnDate(calendar.getTime());

        lendDetailsRepository.save(lendDetails);
    }

}

