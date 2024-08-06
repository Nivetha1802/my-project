package com.library.service;

import com.library.entity.Books;
import java.util.*;

public interface BooksService {


    List<Books> getAllBooks();

    Books getBookById(Integer bookid);

    Books createBook(Books book);

    Books updateBook(Integer bookid, Books bookDetails);

    void deleteBook(Integer bookid);
 
}

