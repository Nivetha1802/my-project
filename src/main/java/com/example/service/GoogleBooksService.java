package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import com.example.model.GoogleBooks;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GoogleBooksService {
    
    private static final String GOOGLE_BOOKS_API_BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    @Value("${google.books.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GoogleBooksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GoogleBooks searchBook(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_BOOKS_API_BASE_URL)
                .queryParam("q", query)
                .queryParam("key", apiKey) // Include API key here
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return parseBook(response.getBody());
    }

    // public GoogleBooks searchBookById(String id) {
    //     String url = "https://www.googleapis.com/books/v1/volumes/" + id + "?key=" + apiKey;
    //     String response = restTemplate.getForObject(url, String.class);
    //     return parseBook(response);
    // }

    // public List<GoogleBooks> searchBooksByTitle(String title) {
    //     String url = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title + "&key=" + apiKey;
    //     String response = restTemplate.getForObject(url, String.class);
    //     return parseBooks(response);
    // }

    // public List<GoogleBooks> searchBooksByAuthor(String author) {
    //     String url = "https://www.googleapis.com/books/v1/volumes?q=inauthor:" + author + "&key=" + apiKey;
    //     String response = restTemplate.getForObject(url, String.class);
    //     return parseBooks(response);
    // }
    
    private GoogleBooks parseBook(String response) {
        GoogleBooks book = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode items = root.path("items");

            if (items.isArray() && items.size() > 0) {
                JsonNode item = items.get(0); // Get the first item
                JsonNode volumeInfo = item.path("volumeInfo");
                book = new GoogleBooks();
                book.setId(item.path("id").asText());
                book.setTitle(volumeInfo.path("title").asText());
                book.setAuthors(mapper.convertValue(volumeInfo.path("authors"), List.class));
                book.setPublisher(volumeInfo.path("publisher").asText());
                book.setPublishedDate(volumeInfo.path("publishedDate").asText());
                book.setDescription(volumeInfo.path("description").asText());
                book.setThumbnail(volumeInfo.path("imageLinks").path("thumbnail").asText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }


    // private List<GoogleBooks> parseBooks(String response) {
    //     List<GoogleBooks> books = new ArrayList<>();
    //     try {
    //         ObjectMapper mapper = new ObjectMapper();
    //         JsonNode root = mapper.readTree(response);
    //         JsonNode items = root.path("items");

    //         if (items.isArray()) {
    //             for (JsonNode item : items) {
    //                 GoogleBooks book = new GoogleBooks();
    //                 JsonNode volumeInfo = item.path("volumeInfo");

    //                 book.setId(item.path("id").asText());
    //                 book.setTitle(volumeInfo.path("title").asText());
    //                 book.setAuthors(mapper.convertValue(volumeInfo.path("authors"), List.class));
    //                 book.setPublisher(volumeInfo.path("publisher").asText());
    //                 book.setPublishedDate(volumeInfo.path("publishedDate").asText());
    //                 book.setDescription(volumeInfo.path("description").asText());
    //                 book.setThumbnail(volumeInfo.path("imageLinks").path("thumbnail").asText());

    //                 books.add(book);
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return books;
    // }
}
