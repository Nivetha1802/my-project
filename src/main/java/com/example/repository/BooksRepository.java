package com.example.repository;

import com.example.entity.Books;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {


    @Modifying
    @Query("UPDATE Books b SET b.bookcount = b.bookcount - 1 WHERE b.bookid = :bookId")
    void decrementBookCount(@Param("bookId") Integer bookId);

    @Modifying
    @Query("UPDATE Books b SET b.bookcount = b.bookcount + 1 WHERE b.bookid = :bookId")
    void incrementBookCount(@Param("bookId") Integer bookId);

    // Collection<? extends Books> findByBookNameContaining(String searchValue);
}

