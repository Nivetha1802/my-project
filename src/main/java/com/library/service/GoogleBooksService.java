package com.library.service;

import java.util.*;
import com.library.model.GoogleBooks;


public interface GoogleBooksService {

    List<GoogleBooks> searchBook(String query);
    List<GoogleBooks> parseBooks(String response);
  
}
