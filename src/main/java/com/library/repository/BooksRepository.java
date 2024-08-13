package com.library.repository;

import org.springframework.stereotype.Repository;
import com.library.entity.Books;

@Repository
public interface BooksRepository extends BaseRepository<Books, Integer> {


}

