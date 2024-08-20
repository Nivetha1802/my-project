package com.library.service;

import com.library.entity.Books;
import com.library.repository.BooksRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService{
   
    
    private BooksRepository booksRepository;

    public BooksServiceImpl(BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }

    @Override
    public List<Books> getAll() {
        return booksRepository.findAll();
    }

    @Override
    public Optional<Books> getById(Integer bookid) {
        return Optional.ofNullable(booksRepository.findById(bookid).orElse(null));
    }

    @Override
    public Books create(Books book) {
        return booksRepository.save(book);
    }

    @Override
    public Books update(Integer bookid, Books bookDetails) {
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
    public void delete(Integer bookid) {
        booksRepository.deleteById(bookid);
    }
}
