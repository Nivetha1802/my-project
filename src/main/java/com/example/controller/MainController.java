package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    private UserService userService;

    private BooksService booksService;

    private LendDetailsService lendDetailsService;

    private final GoogleBooksService googleBooksService;

    private final ObjectMapper objectMapper;

    public MainController(UserService userService, LendDetailsService lendDetailsService, BooksService booksService,
    GoogleBooksService googleBooksService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.lendDetailsService = lendDetailsService;
        this.booksService = booksService;
        this.googleBooksService = googleBooksService;
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
    public String submitRegistration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
            Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:/signup";
        } else {
            session.setAttribute("userId", user.getId());
            System.out.println("user id:" + user.getId());
            System.out.println(user);
            userService.saveUser(user);
            model.addAttribute("message", "Registration successful!");
            String role = user.getRole(); // Assume User has a getRole() method
            return "redirect:/home?role=" + role;
        }

    }

    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute("loginuser") LoginUser loginuser, Model model) {
        model.addAttribute(loginuser);
        return "login";
    }

    @PostMapping("/submitLogin")
    public String submitLogin(@Valid @ModelAttribute("loginuser") LoginUser loginuser, BindingResult bindingResult,
            Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:/login";
        } else {
            // Authenticate the user
            UserEntity user = userService.authenticate(loginuser.getId(), loginuser.getPassword());
            if (user != null) {
                session.setAttribute("userId", user.getId());
                System.out.println("user id:" + user.getId());
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
        System.out.println(booksService.getAllBooks());
        // if (availableBooks != null) {
        //     availableBooks.forEach(book -> System.out.println(book.getBookname())); 
        // }
        model.addAttribute("books", availableBooks);
        return "lend_table";
    }

    @PostMapping("/submitlendtable")
    public String submitLendTable(@RequestParam("selectedBooks") String selectedBooks, HttpSession session)
            throws JsonMappingException, JsonProcessingException {
        List<Books> selectedBooksList = objectMapper.readValue(selectedBooks, new TypeReference<List<Books>>() {
        });
        session.setAttribute("selectedBooks", selectedBooksList);
        System.out.println(selectedBooksList);
        return "redirect:/lendDetails";
    }

    @GetMapping("/lendDetails")
    public String showLendDetails(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<Books> selectedBooks = (List<Books>) session.getAttribute("selectedBooks");
        if (selectedBooks != null) {
            System.out.println("Selected Books: " + selectedBooks);
        } else {
            System.out.println("Selected Books is null");
        }
        model.addAttribute("selectedBooks", selectedBooks);
        System.out.println("model" + selectedBooks);
        return "lend_details";
    }

    @PostMapping("/submitlenddetails")
    public String submitLendDetail(@RequestParam("selectedBooks") String selectedBooks, HttpSession session,
            Model model) throws JsonMappingException, JsonProcessingException {
        List<LendDetails> selectedBooksList = objectMapper.readValue(selectedBooks,
                new TypeReference<List<LendDetails>>() {
                });
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println(userId);
        UserEntity user = userService.getUserById(userId);
        System.out.println(selectedBooksList);

        for (LendDetails book : selectedBooksList) {
            LendDetails lendDetail = new LendDetails();
            lendDetail.setUser(user);
            lendDetail.setBookid(book.getBookid());
            lendDetail.setLendDate(book.getLendDate());
            lendDetail.setReturnDate(book.getReturnDate());
            lendDetail.setRenewDate(null);
            lendDetail.setRenewCount(0);
            lendDetail.setFine(0.0);

            Books bookEntity = booksService.getBookById(book.getBookid());
            lendDetail.setBookname(bookEntity.getBookname());
            lendDetail.setAuthor(bookEntity.getAuthor());
            lendDetail.setInfo(bookEntity.getInfo());
            lendDetail.setSubject(bookEntity.getSubject());

            bookEntity.setBookcount(bookEntity.getBookcount() - 1);
            lendDetailsService.createLendDetails(lendDetail);
            booksService.updateBook(book.getBookid(), bookEntity);

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
            System.out.println(addBook);
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
            System.out.println(updateBook);
            return "redirect:/updateBook";
        } else {
            System.out.println(updateBook);
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
            System.out.println(deleteBook);
            booksService.deleteBook(deleteBook.getBookid());
            model.addAttribute("message", "successful!");
            return "redirect:/librarianHomePage";
        }
    }

    @GetMapping("/allBooks")
    public String showAllBookPage(Model model) {
        List<Books> availableBooks = booksService.getAllBooks();
        System.out.println(booksService.getAllBooks());
        if (availableBooks != null) {
            availableBooks.forEach(book -> System.out.println(book.getBookname())); // Debugging
        }
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
        // System.out.println("hii"+ search.getQuery());
        if (search == null || search.getQuery() == null) {
            model.addAttribute("message", "No search query provided.");
            return "search";
        }
        GoogleBooks books = googleBooksService.searchBook(search.getQuery());
        System.out.println(books);
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


    // @GetMapping("/search")
    // public String searchBooks(@RequestParam String query) {
    //     return googleBooksService.searchBooks(query);
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


}
