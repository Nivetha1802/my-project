package com.example.service;

import com.example.entity.Books;
import com.example.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public void returnBooks(List<Integer> bookIds) {
        for (Integer bookId : bookIds) {
            Optional<Books> book = booksRepository.findById(bookId);
            if (book.isPresent()) {
                // Increase count when returning book
                booksRepository.incrementBookCount(bookId);
            }
        }
    }

    // public List<Books> searchBooks(List<String> filters, String searchValue) {
        
    //     if (filters == null || filters.isEmpty()) {
            
    //         return Collections.emptyList();
    //     }
    
    //     List<Books> books = new ArrayList<>();
    
    //     for (String filter : filters) {
    //         switch (filter) {
    //             case "bookname":
    //                 books.addAll(booksRepository.findByBooknameContaining(searchValue));
    //                 break;
    //             case "subject":
    //                 books.addAll(booksRepository.findByBooknameContaining(searchValue));
    //                 break;
    //             case "author":
    //                 books.addAll(booksRepository.findByBooknameContaining(searchValue));
    //                 break;
    //         }
    //     }
    
    //     // Remove duplicates if any
    //     Set<Books> uniqueBooks = new LinkedHashSet<>(books);
    //     return new ArrayList<>(uniqueBooks);
    // }

    public List<Books> getBooksByIds(List<Long> bookIds) {
        return booksRepository.findByIds(bookIds);
    }
    
}

