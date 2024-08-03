// package com.example.service;

// import com.example.entity.OLBooks;
// import com.example.model.GoogleBooks;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.util.UriComponentsBuilder;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;

// @Service
// public class OLBooksService {

//      private static final String OPEN_LIBRARY_API_BASE_URL = "https://openlibrary.org/search.json";

//     private final RestTemplate restTemplate;

//     // @Value("${open.library.api.key}")
//     // private String apiKey;  // If required

//     public OLBooksService(RestTemplate restTemplate) {
//         this.restTemplate = restTemplate;
//     }

//     public List<GoogleBooks> getBooks(int page, int pageSize) {
//         String url = UriComponentsBuilder.fromHttpUrl(OPEN_LIBRARY_API_BASE_URL)
//                 .queryParam("page", page)
//                 .queryParam("limit", pageSize)
//                 .toUriString();

//         String response = restTemplate.getForObject(url, String.class);
//         return parseBooks(response);
//     }

//     private List<GoogleBooks> parseBooks(String response) {
//         List<GoogleBooks> books = new ArrayList<>();
//         try {
//             // Example parsing logic, adapt based on the actual response structure
//             ObjectMapper mapper = new ObjectMapper();
//             JsonNode root = mapper.readTree(response);
//             JsonNode docs = root.path("docs");

//             if (docs.isArray()) {
//                 for (JsonNode item : docs) {
//                     GoogleBooks book = new GoogleBooks();
//                     book.setId(item.path("key").asText());
//                     book.setTitle(item.path("title").asText());
//                     book.setAuthors(mapper.convertValue(item.path("author_name"), List.class));
//                     book.setPublisher(item.path("publisher").asText());
//                     book.setPublishedDate(item.path("publish_date").asText());
//                     book.setDescription(item.path("first_sentence").asText());

//                     books.add(book);
//                 }
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return books;
//     }
// }

//     // public List<OLBooks> searchBooks(String query, String searchType, int page, int size) throws IOException {
//     //     String url = buildSearchUrl(query, searchType, page);
//     //     ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

//     //     JsonNode root = objectMapper.readTree(response.getBody());
//     //     JsonNode docs = root.path("docs");

//     //     List<OLBooks> books = new ArrayList<>();
//     //     for (JsonNode doc : docs) {
//     //         OLBooks book = new OLBooks();
//     //         book.setTitle(doc.path("title").asText());
//     //         book.setAuthors(new ArrayList<>());
//     //         for (JsonNode author : doc.withArray("author_name")) {
//     //             book.getAuthors().add(author.asText());
//     //         }
//     //         book.setIsbn(doc.path("isbn").isArray() ? doc.withArray("isbn").get(0).asText() : "");
//     //         book.setPublisher(doc.path("publisher").isArray() ? doc.withArray("publisher").get(0).asText() : "");
//     //         book.setPublishedDate(doc.path("first_publish_year").asText());
//     //         book.setDescription(doc.path("subject").isArray() ? doc.withArray("subject").get(0).asText() : "");
//     //         book.setThumbnail(""); // Thumbnail can be extracted from the cover ID if available.
//     //         books.add(book);
//     //     }

//     //     return books;
//     // }

//     // private String buildSearchUrl(String query, String searchType, int page) {
//     //     String baseUrl = "http://openlibrary.org/search.json";
//     //     switch (searchType) {
//     //         case "title":
//     //             return baseUrl + "?title=" + query + "&page=" + page;
//     //         case "isbn":
//     //             return baseUrl + "?isbn=" + query + "&page=" + page;
//     //         case "author":
//     //             return baseUrl + "?author=" + query + "&page=" + page;
//     //         default:
//     //             return baseUrl + "?q=" + query + "&page=" + page;
//     //     }
//     // }

