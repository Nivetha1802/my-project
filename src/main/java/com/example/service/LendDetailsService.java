package com.example.service;

import com.example.entity.Lend;
import com.example.entity.LendDetails;
import com.example.entity.UserEntity;
import com.example.repository.LendDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LendDetailsService {

    @Autowired
    private LendDetailsRepository lendDetailsRepository;

    public List<LendDetails> getAllLendDetails() {
        return lendDetailsRepository.findAll();
    }

    public Optional<LendDetails> getLendDetailsById(Integer lendId) {
        return lendDetailsRepository.findById(lendId);
    }
    @Transactional
    public LendDetails addLendDetails(LendDetails lendDetails) {
        return lendDetailsRepository.save(lendDetails);
    }
    @Transactional
    public LendDetails updateLendDetails(Integer lendId, LendDetails lendDetails) {
        Optional<LendDetails> existingLendDetails = lendDetailsRepository.findById(lendId);
        if (existingLendDetails.isPresent()) {
            lendDetails.setLendId(lendId);
            return lendDetailsRepository.save(lendDetails);
        } else {
            throw new RuntimeException("LendDetails not found with id " + lendId);
        }
    }
    @Transactional
    public void deleteLendDetails(Integer lendId) {
        lendDetailsRepository.deleteById(lendId);
    }

    public void processBookLendDetails(LendDetails book, UserEntity user) {
        LendDetails lendDetail = new LendDetails();
        lendDetail.setUser(user);
        lendDetail.setId(book.getId());
        lendDetail.setTitle(book.getTitle());
        lendDetail.setAuthors(book.getAuthors());
        lendDetail.setLendDate(book.getLendDate());
        lendDetail.setReturnDate(book.getReturnDate());
        lendDetail.setRenewCount(0);
        lendDetail.setFine(0.0);
        addLendDetails(lendDetail);
        System.out.println(lendDetail.toString());
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

