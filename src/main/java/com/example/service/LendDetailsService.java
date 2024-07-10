// package com.example.service;

// import com.example.entity.LendDetails;
// import com.example.repository.LendDetailsRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// // import java.util.Date;

// @Service
// public class LendDetailsService {

//     private final LendDetailsRepository lendDetailsRepository;

//     @Autowired
//     public LendDetailsService(LendDetailsRepository lendDetailsRepository) {
//         this.lendDetailsRepository = lendDetailsRepository;
//     }

//     @Transactional
//     public LendDetails createLendDetails(LendDetails lendDetails) {
//         return lendDetailsRepository.save(lendDetails);
//     }

//     @Transactional
//     public void deleteLendDetails(Long lendId) {
//         lendDetailsRepository.deleteById(lendId);
//     }

  
// }

