package com.example.service;

import com.example.entity.Books;
import com.example.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    public Books getBookById(Integer bookid) {
        return booksRepository.findById(bookid).orElse(null);
    }

    public Books createBook(Books book) {
        return booksRepository.save(book);
    }

    public Books updateBook(Integer bookid, Books bookDetails) {
        Books book = booksRepository.findById(bookid).orElse(null);
        if (book != null) {
            book.setBookname(bookDetails.getBookname());
            book.setAuthor(bookDetails.getAuthor());
            book.setSubject(bookDetails.getSubject());
            book.setInfo(bookDetails.getInfo());
            return booksRepository.save(book);
        }
        return null;
    }

    public void deleteBook(Integer bookid) {
        booksRepository.deleteById(bookid);
    }
}

