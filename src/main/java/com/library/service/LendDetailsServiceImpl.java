package com.library.service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.library.entity.LendDetails;
import com.library.entity.UserEntity;
import com.library.repository.LendDetailsRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class LendDetailsServiceImpl implements LendDetailsService{
    
    
    private LendDetailsRepository lendDetailsRepository;

   
    public LendDetailsServiceImpl (LendDetailsRepository lendDetailsRepository){
        this.lendDetailsRepository = lendDetailsRepository;
    }

    @Override
    public List<LendDetails> getAllLendDetails() {
        return lendDetailsRepository.findAll();
    }

    @Override
    public Optional<LendDetails> getLendDetailsById(Integer lendId) {
        return lendDetailsRepository.findById(lendId);
    }

    @Override
    @Transactional
    public LendDetails addLendDetails(LendDetails lendDetails) {
        return lendDetailsRepository.save(lendDetails);
    }

    @Override
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

    @Override
    @Transactional
    public void deleteLendDetails(Integer lendId) {
        lendDetailsRepository.deleteById(lendId);
    }

    @Override
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
    }

    @Override
    public List<LendDetails> getLendDetailsByUserId(Integer id) {
        List<LendDetails> lendDetailsList = lendDetailsRepository.findLendDetailsById(id);
        LocalDate actualReturnDate = LocalDate.now();
        for (LendDetails lendDetails : lendDetailsList) {
            lendDetails.calculateFine(actualReturnDate);
        }
        return lendDetailsList;
    }

    @Override
    @Transactional
    public void renewBook(Integer lendId) {
        LendDetails lendDetails = lendDetailsRepository.findById(lendId).orElseThrow(() -> new IllegalArgumentException("Invalid lend ID"));
        lendDetails.setRenewCount(lendDetails.getRenewCount() + 1);
        lendDetails.setReturnDate(lendDetails.getReturnDate().plusDays(14)); 
        lendDetailsRepository.save(lendDetails);
    }
}
