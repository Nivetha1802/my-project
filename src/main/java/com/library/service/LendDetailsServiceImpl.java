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
public class LendDetailsServiceImpl implements BaseService<LendDetails, Integer>{

    private LendDetailsRepository lendDetailsRepository;

    public LendDetailsServiceImpl(LendDetailsRepository lendDetailsRepository) {
        this.lendDetailsRepository = lendDetailsRepository;
    }

    @Override
    public List<LendDetails> getAll() {
        return lendDetailsRepository.findAll();
    }

    @Override
    public Optional<LendDetails> getById(Integer id) {
        return lendDetailsRepository.findById(id);
    }

    @Override
    @Transactional
    public LendDetails create(LendDetails lendDetails) {
        return lendDetailsRepository.save(lendDetails);
    }

    @Override
    @Transactional
    public LendDetails update(Integer id, LendDetails lendDetails) {
        Optional<LendDetails> existingLendDetails = lendDetailsRepository.findById(id);
        if (existingLendDetails.isPresent()) {
            lendDetails.setId(id);
            return lendDetailsRepository.save(lendDetails);
        } else {
            throw new RuntimeException("LendDetails not found with id " + id);
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        lendDetailsRepository.deleteById(id);
    }

    
    public void processBookLendDetails(LendDetails book, Optional<UserEntity> user) {
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            LendDetails lendDetail = new LendDetails();
            lendDetail.setUser(userEntity);
            lendDetail.setTitle(book.getTitle());
            lendDetail.setAuthors(book.getAuthors());
            lendDetail.setLendDate(book.getLendDate());
            lendDetail.setReturnDate(book.getReturnDate());
            lendDetail.setRenewCount(0);
            lendDetail.setFine(0.0);
            lendDetail.setBookid(book.getBookid());
            create(lendDetail);
        } else {
            throw new IllegalArgumentException("User must be present");
        }
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
    public void renewBook(Integer id) {
        LendDetails lendDetails = lendDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lend ID"));
        lendDetails.setRenewCount(lendDetails.getRenewCount() + 1);
        lendDetails.setReturnDate(lendDetails.getReturnDate().plusDays(14));
        lendDetailsRepository.save(lendDetails);
    }



}
