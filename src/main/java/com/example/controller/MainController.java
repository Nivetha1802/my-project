package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.entity.*;
import com.example.model.*;
import com.example.service.*;



@Controller
public class MainController {

    private final UserService userService;

    private final BooksService booksService;

    private final LendDetailsService lendDetailsService;

    private final GoogleBooksService googleBooksService;

    private final ObjectMapper objectMapper;

    private final OLBooksService olBooksService;

    public MainController(UserService userService, LendDetailsService lendDetailsService, BooksService booksService,
    GoogleBooksService googleBooksService, OLBooksService olBooksService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.lendDetailsService = lendDetailsService;
        this.booksService = booksService;
        this.googleBooksService = googleBooksService;
        this.olBooksService = olBooksService;
        this.objectMapper = objectMapper;
    }

    @GetMapping({ "/", "/logout" })
    public String getHomePage() {
        return "login";
    }

    @GetMapping("/home")
    public String getHomePage(@RequestParam("role") String role, Model model) {
        if ("student".equalsIgnoreCase(role) || "teacher".equalsIgnoreCase(role)) {
            return "studentHomePage";
        } else {
            return "librarianHomePage";
        }
    }

    @GetMapping("/studentHomePage")
    public String getstudentHomePage() {
        return "studentHomePage";
    }
    
    @GetMapping("/librarianHomePage")
    public String getLibrarianHomePage() {
        return "librarianHomePage";
    }

    @GetMapping("/signup")
    public String showSignupPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute(user);
        return "signup";
    }

    @PostMapping("/submitRegistration")
    public String submitRegistrationFormDetails(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
            Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "signup";
        } else {
            session.setAttribute("userId", user.getId());
            userService.saveUser(user);
            model.addAttribute("message", "Registration successful!");
            String role = user.getRole();
            return "redirect:/home?role=" + role;
        }

    }

    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute("loginuser") LoginUser loginuser, Model model) {
        model.addAttribute(loginuser);
        return "login";
    }

    @PostMapping("/submitLogin")
    public String submitLoginFormDetails(@Valid @ModelAttribute("loginuser") LoginUser loginuser, BindingResult bindingResult,
            Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        } else {
            UserEntity user = userService.authenticate(loginuser.getId(), loginuser.getPassword());
            if (user != null) {
                session.setAttribute("userId", user.getId());
                model.addAttribute("message", "Login successful!");
                String role = user.getRole();
                return "redirect:/home?role=" + role;
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/lendtable")
    public String showLendtablePage(Model model) {

        List<Books> availableBooks = booksService.getAllBooks();
        model.addAttribute("books", availableBooks);
        return "lend_table";
    }

    @PostMapping("/submitlendtable")
    public String submitLendTable(@RequestParam("selectedBooks") String selectedBooks, HttpSession session)
            throws JsonMappingException, JsonProcessingException {
        List<Books> selectedBooksList = objectMapper.readValue(selectedBooks, new TypeReference<List<Books>>() {
        });
        session.setAttribute("selectedBooks", selectedBooksList);
        return "redirect:/lendDetails";
    }

    @GetMapping("/lendDetails")
    public String showLendDetails(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<Books> selectedBooks = (List<Books>) session.getAttribute("selectedBooks");
        model.addAttribute("selectedBooks", selectedBooks);
        return "lend_details";
    }

    @PostMapping("/submitlenddetails")
    public String submitLendDetail(@RequestParam("selectedBooks") String selectedBooks, HttpSession session,
            Model model) throws JsonMappingException, JsonProcessingException {
        List<LendDetails> selectedBooksList = objectMapper.readValue(selectedBooks,
                new TypeReference<List<LendDetails>>() {
                });
        Integer userId = (Integer) session.getAttribute("userId");
        UserEntity user = userService.getUserById(userId);

        for (LendDetails book : selectedBooksList) {
           lendDetailsService.processLendDetails(book, user);
        }
        model.addAttribute("message", "Successfully Lent books");
        return "redirect:/studentHomePage";
    }


    @GetMapping("/returntable")
    public String showReturnBooksPage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<LendDetails> lendBooks = lendDetailsService.getLendDetailsByUserId(userId);
        model.addAttribute("lendBooks", lendBooks);
        return "return_table";
    }

    @PostMapping("/submitReturnBooks")
    public String submitReturnBooks(@RequestParam("selectedBooks") String selectedBooks,
            HttpSession session) throws JsonMappingException, JsonProcessingException {
        List<LendDetails> selectedBooksList = objectMapper.readValue(selectedBooks,
                new TypeReference<List<LendDetails>>() {
                });

        for (LendDetails book : selectedBooksList) {
            Books bookEntity = booksService.getBookById(book.getBookid());
            bookEntity.setBookcount(bookEntity.getBookcount() + 1);
            booksService.updateBook(bookEntity.getBookid(), bookEntity);
            lendDetailsService.deleteLendDetails(book.getLendId());
        }

        session.removeAttribute("selectedBooks");

        return "redirect:/studentHomePage";
    }

    @GetMapping("/renewtable")
    public String showRenewtablePage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<LendDetails> lendBooks = lendDetailsService.getLendDetailsByUserId(userId);
        model.addAttribute("lendBooks", lendBooks);
        return "renew_table";
    }

    @PostMapping("/submitRenewtable")
    public String submitRenewtable(@RequestParam("selectedBooks") String selectedBooks,
            HttpSession session) throws JsonMappingException, JsonProcessingException {
        List<LendDetails> selectedBooksList = objectMapper.readValue(selectedBooks,new TypeReference<List<LendDetails>>() {});
        for (LendDetails book : selectedBooksList) {
            lendDetailsService.renewBook(book.getLendId());
        }
        return "redirect:/studentHomePage";
    }

    @GetMapping("/fineDetails")
    public String showFineDetailsPage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        UserEntity user = userService.getUserById(userId);
        String username = user.getName();
        List<LendDetails> lendBooks = lendDetailsService.getLendDetailsByUserId(userId);
        List<LendDetails> lendBooksWithFine = lendBooks.stream()
        .filter(lendDetail -> lendDetail.getFine() > 0.0)
        .collect(Collectors.toList());
        model.addAttribute("books", lendBooksWithFine);
        model.addAttribute("user", username);
        return "fine_table";
    }

    @GetMapping("/bookManagement")
    public String showBookManagementPage(Model model) {
        AddBook addBook = new AddBook();
        model.addAttribute(addBook);
        return "bookManagement";
    }

    @GetMapping("/addBook")
    public String showAddBookPage(Model model) {
        Books addBook = new Books();
        model.addAttribute(addBook);
        return "bookManagement";
    }

    @PostMapping("/submitAddBook")
    public String submitAddBook(@Valid @ModelAttribute("addBook") Books addBook, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/bookManagement";
        } else {
            booksService.createBook(addBook);
            model.addAttribute("message", "successful!");
            return "redirect:/librarianHomePage";
        }
    }

    @GetMapping("/updateBook")
    public String showUpdateBookPage(Model model) {
        UpdateBook updateBook = new UpdateBook();
        model.addAttribute(updateBook);
        return "updateBook";
    }

    @PostMapping("/submitUpdateBook")
    public String submitUpdateBook(@Valid @ModelAttribute("updateBook") Books updateBook,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/updateBook";
        } else {
            booksService.updateBook(updateBook.getBookid(), updateBook);
            model.addAttribute("message", "successful!");
            return "redirect:/librarianHomePage";
        }
    }

    @GetMapping("/deleteBook")
    public String showDeleteBookPage(Model model) {
        Books deleteBook = new Books();
        model.addAttribute(deleteBook);
        return "deleteBook";
    }

    @PostMapping("/submitDeleteBook")
    public String submitDeleteBook(@Valid @ModelAttribute("deleteBook") Books deleteBook,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/deleteBook";
        } else {
            booksService.deleteBook(deleteBook.getBookid());
            model.addAttribute("message", "successful!");
            return "redirect:/librarianHomePage";
        }
    }

    @GetMapping("/allBooks")
    public String showAllBookPage(Model model) {
        List<Books> availableBooks = booksService.getAllBooks();
        model.addAttribute("books", availableBooks);
        return "allBooks";
    }

    @GetMapping("/getBookDetails")
    @ResponseBody
    public ResponseEntity<Books> getBookDetails(@RequestParam Integer bookid) {
        Books book = booksService.getBookById(bookid);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
   
    @GetMapping("/search")
    public String getSearchResults(HttpSession session, Model model) {
        Search search = (Search) session.getAttribute("searchquery");
        System.out.println("hii"+search);
        if (search == null || search.getQuery() == null) {
            model.addAttribute("message", "No search query provided.");
            return "search";
        }
        Books books = booksService.getBookById(search.getQuery());
        
        model.addAttribute("books", books);
        model.addAttribute("search", search);
        return "search";
    }

    @PostMapping("/search")
    public String searchForm(@Valid @ModelAttribute("search") Search search, BindingResult bindingResult, HttpSession session) {
        System.out.println("hii");
        if (bindingResult.hasErrors()) {
            return "search";
        }
        session.setAttribute("searchquery", search);
        System.out.println("hii"+search.getQuery());
        return "redirect:/search";
    }

    @GetMapping("/searchBooks")
    public ResponseEntity<GoogleBooks> searchBooks(@RequestParam String query) {
    System.out.println(query);
    GoogleBooks book = googleBooksService.searchBook(query);
    System.out.println(book);
    System.out.println(book);
    if (book != null) {
        return new ResponseEntity<>(book, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
}


    // @GetMapping("/searchBooks")
    // public ResponseEntity<GoogleBooks> searchBooks(@RequestParam String query) {
    //     GoogleBooks book = googleBooksService.searchBook(query);
    //     if (book != null) {
    //         return new ResponseEntity<>(book, HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

//     @GetMapping("/searchBooks")
//     public String searchBooks(@RequestParam("query") String query,
//                               @RequestParam("searchType") String searchType,
//                               @RequestParam(value = "page", defaultValue = "1") int page,
//                               @RequestParam(value = "size", defaultValue = "10") int size,
//                               Model model) {
//         try {
//             System.out.println("hii");
//             List<OLBooks> books = olBooksService.searchBooks(query, searchType, page, size);
//             model.addAttribute("books", books);
//             model.addAttribute("query", query);
//             model.addAttribute("searchType", searchType);
//             model.addAttribute("page", page);
//             model.addAttribute("size", size);
//         } catch (IOException e) {
//             model.addAttribute("error", "An error occurred while fetching data from Open Library.");
//         }
//         return "bookList";
//     }
// }




    // @GetMapping("/search")
    // public String searchBooks(@RequestParam String searchType, @RequestParam String query, Model model) {
    //     List<GoogleBooks> books = null;
    //     if ("id".equals(searchType)) {
    //         GoogleBooks book = googleBooksService.searchBookById(query);
    //         if (book != null) {
    //             books = List.of(book);
    //         }
    //     } else if ("title".equals(searchType)) {
    //         books = googleBooksService.searchBooksByTitle(query);
    //     } else if ("author".equals(searchType)) {
    //         books = googleBooksService.searchBooksByAuthor(query);
    //     }

    //     model.addAttribute("books", books);
    //     return "search";
    // }

    // @PostMapping("/search")
    // public String searchForm(@Valid @ModelAttribute("search") Search search, BindingResult bindingResult, HttpSession session) {
    //     System.out.println("hii");
    //     if (bindingResult.hasErrors()) {
    //         return "search";
    //     }
    //     session.setAttribute("searchquery", search);
    //     System.out.println("hii"+search.getQuery());
    //     return "redirect:/search";
    // }

   
 // @GetMapping("/searchQuery")
    // public String getSearch(Model model, HttpSession session) {
    //     Search search = new Search();
    //     model.addAttribute("search", search);
    //     Search searchresult = (Search) session.getAttribute("searchquery");
    //     System.out.println("hii"+searchresult);
    //     // System.out.println("hii"+ searchresult.getQuery());
    //     if (searchresult == null || searchresult.getQuery() == null) {
    //         model.addAttribute("message", "No search query provided.");
    //         model.addAttribute("books", Collections.emptyList()); // Ensure books attribute is present even if empty 
    //         return "search";
    //     }
    //     Books books = booksService.getBookById(searchresult.getQuery());
        
    //     model.addAttribute("books", books);
    //     model.addAttribute("search", searchresult);
    //     return "search";
    // }

     // @GetMapping("/lendBook")
    // public String showLendBookPage(Model model) {
    // Lend lend = new Lend();
    // model.addAttribute(lend);
    // return "lendBook";
    // }

    
    // @PostMapping("/returnBooks")
    // public String returnBooks(@RequestParam("bookIds") List<Integer> bookIds,
    // Model model) {
    // booksService.returnBooks(bookIds);
    // return "redirect:/lend";
    // }

    
//     @GetMapping("/searchBooksforLending")
// public ResponseEntity<List<Books>> searchBooksforLending(@RequestParam(required = false) Integer query) {
//     List<Books> books;
//     if (query != null) {
//         Books book = booksService.getBookById(query);
//         books = book != null ? Collections.singletonList(book) : Collections.emptyList();
//     } else {
//         books = booksService.getAllBooks();
//     }
//     return new ResponseEntity<>(books, HttpStatus.OK);
// }


    // @PostMapping("/submitReturnBook")
    // public String submitReturnBook(@Valid @ModelAttribute("returning") Returning
    // returning, BindingResult bindingResult,
    // Model model) {
    // if (bindingResult.hasErrors()) {
    // System.out.println(returning);
    // return "returnBook";
    // } else {
    // System.out.println(returning);
    // model.addAttribute("message", "Return successful!");
    // return "studentHomePage";
    // }
    // }

    // @PostMapping("/submitRenewBook")
    // public String submitRenewBook(@Valid @ModelAttribute("renew") Renew renew, BindingResult bindingResult,
    //         Model model) {
    //     if (bindingResult.hasErrors()) {
    //         System.out.println(renew);
    //         return "redirect:/renewBook";
    //     } else {
    //         System.out.println(renew);
    //         model.addAttribute("message", "Renew successful!");
    //         return "redirect:/studentHomePage";
    //     }
    // }

    // @PostMapping("/submitLendBook")
    // public String submitLendBook(@Valid @ModelAttribute("lend") Lend lend, BindingResult bindingResult, Model model) {
    //     if (bindingResult.hasErrors()) {
    //         return "redirect:/lendBook";
    //     } else {
    //         System.out.println(lend);
    //         // lendDetailsService.createLendDetails(lend);
    //         model.addAttribute("message", "Lending successful!");
    //         return "redirect:/studentHomePage";
    //     }
    // }

    // @PostMapping("/submitFineDetails")
    // public String submitFineDetails(@Valid @ModelAttribute("finedet") FineDetails finedet, BindingResult bindingResult,
    //         Model model) {
    //     if (bindingResult.hasErrors()) {
    //         return "redirect:/fineDetails";
    //     } else {
    //         System.out.println(finedet);
    //         model.addAttribute("message", "successful!");
    //         return "redirect:/studentHomePage";
    //     }
    // }



