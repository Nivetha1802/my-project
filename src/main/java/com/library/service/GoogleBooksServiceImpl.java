package com.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.Dto.GoogleBooks;

import java.util.List;
import java.util.ArrayList;

@Service
public class GoogleBooksServiceImpl {
    
    private final String GOOGLE_BOOKS_API_BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    @Value("${google.books.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GoogleBooksServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<GoogleBooks> searchBook(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_BOOKS_API_BASE_URL)
                .queryParam("q", query)
                .queryParam("key", apiKey) 
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return parseBooks(response.getBody());
    }

    
    public List<GoogleBooks> parseBooks(String response) {
        List<GoogleBooks> books = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode items = root.path("items");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    GoogleBooks book = new GoogleBooks();
                    JsonNode volumeInfo = item.path("volumeInfo");

                    book.setId(item.path("id").asText());
                    book.setTitle(volumeInfo.path("title").asText());
                    JsonNode authorsNode = volumeInfo.path("authors");
                    if (authorsNode.isArray()) {
                        List<String> authorsList = new ArrayList<>();
                        for (JsonNode authorNode : authorsNode) {
                            authorsList.add(authorNode.asText());
                        }
                        book.setAuthors(String.join(", ", authorsList));
                    } else {
                        book.setAuthors(""); 
                    }
                    book.setPublisher(volumeInfo.path("publisher").asText());
                    book.setPublishedDate(volumeInfo.path("publishedDate").asText());
                    book.setDescription(volumeInfo.path("description").asText());
                    book.setThumbnail(volumeInfo.path("imageLinks").path("thumbnail").asText());
                    
                    books.add(book);
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
