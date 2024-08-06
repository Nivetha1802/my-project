package com.library.service;

import com.library.entity.Books;
import com.library.repository.BooksRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService{
   
    @Autowired
    private BooksRepository booksRepository;

    @Override
    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    public Books getBookById(Integer bookid) {
        return booksRepository.findById(bookid).orElse(null);
    }

    @Override
    public Books createBook(Books book) {
        return booksRepository.save(book);
    }

    @Override
    public Books updateBook(Integer bookid, Books bookDetails) {
        Books book = booksRepository.findById(bookid).orElse(null);
        if (book != null) {
            book.setBookname(bookDetails.getBookname());
            book.setAuthor(bookDetails.getAuthor());
            book.setSubject(bookDetails.getSubject());
            book.setInfo(bookDetails.getInfo());
            book.setBookcount(bookDetails.getBookcount());
            return booksRepository.save(book);
        }
        return null;
    }

    @Override
    public void deleteBook(Integer bookid) {
        booksRepository.deleteById(bookid);
    }
}
