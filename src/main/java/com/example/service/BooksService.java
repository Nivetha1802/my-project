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


    public List<Books> lendBooks(List<Integer> bookIds) {
        List<Books> lendedBooks = new ArrayList<>();
        for (Integer bookId : bookIds) {
            Optional<Books> book = booksRepository.findById(bookId);
            if (book.isPresent() && book.get().getBookcount() > 0) {
                booksRepository.decrementBookCount(bookId);
                lendedBooks.add(book.get());
            }
        }
        return lendedBooks;
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
    //     // Implement logic to search books based on the selected filters and search value
    //     if (filters == null || filters.isEmpty()) {
    //         // Handle case where no filters are selected
    //         return Collections.emptyList();
    //     }
    
    //     List<Books> books = new ArrayList<>();
    
    //     for (String filter : filters) {
    //         switch (filter) {
    //             case "bookname":
    //                 books.addAll(booksRepository.findByBookNameContaining(searchValue));
    //                 break;
    //             case "subject":
    //                 books.addAll(booksRepository.findByBookNameContaining(searchValue));
    //                 break;
    //             case "author":
    //                 books.addAll(booksRepository.findByBookNameContaining(searchValue));
    //                 break;
    //             default:
    //                 // Handle unknown filter (optional)
    //                 break;
    //         }
    //     }
    
    //     // Remove duplicates if any
    //     Set<Books> uniqueBooks = new LinkedHashSet<>(books);
    //     return new ArrayList<>(uniqueBooks);
    // }
    
}

