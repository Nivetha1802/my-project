package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class fooController {

    private UserService userService;

    private BooksService booksService;

    private LendDetailsService lendDetailsService;

    private final ObjectMapper objectMapper;

    public fooController(UserService userService, LendDetailsService lendDetailsService, BooksService booksService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.lendDetailsService = lendDetailsService;
        this.booksService = booksService;
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

    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute("loginuser") LoginUser loginuser, Model model) {
        model.addAttribute(loginuser);
        return "login";
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
            return "signup";
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

    @PostMapping("/submitLogin")
    public String submitLogin(@Valid @ModelAttribute("loginuser") LoginUser loginuser, BindingResult bindingResult,
            Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
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
                return "login";
            }
        }
    }

    // @GetMapping("/GetAllUsers")
    // public List<UserEntity> getAllUsers(){
    // System.out.println("req sent");
    // return userService.getAllUsers();
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
    // Optional<UserEntity> user = Optional.ofNullable(userService.getUserById(id));
    // return user.map(ResponseEntity::ok)
    // .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    // @PostMapping("/users")
    // public ResponseEntity<UserEntity> createUser(UserEntity user) {
    // UserEntity savedUser = userService.createUser(user);
    // System.out.println("req sent");
    // return ResponseEntity.ok(savedUser);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<UserEntity> getUserByIdMethod(@PathVariable Integer id)
    // {
    // UserEntity user = userService.getUserById(id);
    // System.out.println("req sent");
    // return ResponseEntity.ok(user);
    // }

    @GetMapping("/returnbook")
    public String showHomePage(Model model) {
        Returning returning = new Returning();
        model.addAttribute(returning);
        return "returnBook";
    }

    @GetMapping("/lendBook")
    public String showLendBookPage(Model model) {
        Lend lend = new Lend();
        model.addAttribute(lend);
        return "lendBook";
    }

    @GetMapping("/lendtable")
    public String showLendtablePage(Model model) {

        List<Books> availableBooks = booksService.getAllBooks();
        System.out.println(booksService.getAllBooks());
        if (availableBooks != null) {
            availableBooks.forEach(book -> System.out.println(book.getBookname())); // Debugging
        }
        model.addAttribute("books", availableBooks);
        return "lend_table";
    }
    // @PostMapping("/lendtable")
    // public String lendBooks(@RequestParam("bookIds") List<Integer> bookIds, Model
    // model) {
    // List<Books> lendedBooks = booksService.lendBooks(bookIds);
    // model.addAttribute("lendedBooks", lendedBooks);
    // return "studenthomepage";
    // }

    @PostMapping("/returnBooks")
    public String returnBooks(@RequestParam("bookIds") List<Integer> bookIds, Model model) {
        booksService.returnBooks(bookIds);
        return "redirect:/lend";
    }

    @PostMapping("/confirmLendBooks")
    public String confirmLendBooks(@RequestParam("bookIds") List<Integer> bookIds,
            @RequestParam("bookNames") List<String> bookNames,
            @RequestParam("bookAuthors") List<String> bookAuthors) {
        List<LendDetails> lendedBooks = new ArrayList<>();
        LocalDate lendDate = LocalDate.now();
        LocalDate returnDate = lendDate.plusDays(14); // Example return date, 14 days later
        for (int i = 0; i < bookIds.size(); i++) {
            LendDetails lend = new LendDetails();
            lend.setBookid(bookIds.get(i));
            lend.setBookname(bookNames.get(i));
            lend.setAuthor(bookAuthors.get(i));
            lend.setLendDate(lendDate);
            lend.setReturnDate(returnDate);
            lendedBooks.add(lend);
        }
        lendDetailsService.saveLendedBooks(lendedBooks);
        return "redirect:/lend";
    }

    @PostMapping("/submitlendtable")
    public String submitLendTable(@RequestParam("selectedBooks") String selectedBooks, HttpSession session) throws JsonMappingException, JsonProcessingException {
        List<Books> selectedBooksList = objectMapper.readValue(selectedBooks, new TypeReference<List<Books>>() {});
        session.setAttribute("selectedBooks", selectedBooksList);
        System.out.println(selectedBooksList);
        return "redirect:/lendDetails";
    }
    @PostMapping("/submitlenddetails")
    public String submitLendDetail(@RequestParam("selectedBooks") String selectedBooks, HttpSession session) throws JsonMappingException, JsonProcessingException {
    List<LendDetails> selectedBooksList = objectMapper.readValue(selectedBooks, new TypeReference<List<LendDetails>>() {});
    Integer userId = (Integer) session.getAttribute("userId");
    System.out.println(userId);
    UserEntity user = userService.getUserById(userId);
    System.out.println(selectedBooksList);
    for (LendDetails book : selectedBooksList) {
        LendDetails lendDetail = new LendDetails();
        lendDetail.setUser(user);
        lendDetail.setBookid(book.getBookid());
        lendDetail.setLendDate(book.getLendDate()); // Set the current date as the lending date
        lendDetail.setReturnDate(book.getReturnDate()); // 14 days after lending date
        lendDetail.setRenewDate(null);
        lendDetail.setRenewCount(0);
        lendDetail.setFine(0.0);
        lendDetailsService.createLendDetails(lendDetail);
        Books bookEntity = booksService.getBookById(book.getBookid());
        booksService.updateBook(book.getBookid(), bookEntity);
    }
    //session.setAttribute("selectedBooks", selectedBooksList);

    return "studentHomePage";
}

    // @GetMapping("/lendDetails")
    // public String showLendDetails(Model model, HttpSession session) {
    //     List<LendDetails> selectedBooks = (List<LendDetails>) session.getAttribute("selectedBooks");
    //     LocalDate lendDate = LocalDate.now();
    //     LocalDate returnDate = lendDate.plusDays(14);
    //     if (selectedBooks != null) {
    //         model.addAttribute("selectedBooks", selectedBooks);
    //         List<LendDetails> lendDetailsList = new ArrayList<>();
    //         for (LendDetails lend : selectedBooks) {
    //             LendDetails lendDetails = new LendDetails();
    //             lendDetails.setBookid(lend.getBookid());
    //             lendDetails.setBookname(lend.getBookname());
    //             lendDetails.setAuthor(lend.getAuthor());
    //             lendDetails.setSubject(lend.getSubject());
    //             lendDetails.setLendDate(lendDate);
    //             lendDetails.setReturnDate(returnDate);
    //             lendDetailsList.add(lendDetails);
    //         }

    //         // Pass bookDetailsList to the view
    //         model.addAttribute("lendDetailsList", lendDetailsList);
    //     } else {
    //         model.addAttribute("error", "No books selected for lending.");
    //     }

    //     return "lend_details";
    // }
    
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
        System.out.println("model"+selectedBooks);
        return "lend_details";
    }

    @GetMapping("/returntable")
    public String showReturnBooksPage(Model model) {
        List<LendDetails> lendBooks = lendDetailsService.getAllLendDetails();
        model.addAttribute("lendBooks", lendBooks);
        return "return_table";
    }

    @PostMapping("/submitReturnBooks")
    public String submitReturnBooks(@RequestParam("bookIds") List<Integer> bookIds,
            HttpSession session) {
        for (int bookId : bookIds) {
            booksService.returnBooks(Arrays.asList(bookId)); // Passing single bookId as list
            lendDetailsService.deleteLendDetails(bookId);
        }

        session.removeAttribute("selectedBooks");

        return "redirect:/studenthomepage"; // Redirect to a success page or another appropriate endpoint

    }

    @GetMapping("/renewbook")
    public String showRenewBookPage(Model model) {
        Renew renew = new Renew();
        model.addAttribute(renew);
        return "renewBook";
    }

    @GetMapping("/fineDetails")
    public String showFineDetailsPage(Model model) {
        List<Books> availableBooks = booksService.getAllBooks();
        System.out.println(booksService.getAllBooks());
        if (availableBooks != null) {
            availableBooks.forEach(book -> System.out.println(book.getBookname())); // Debugging
        }
        model.addAttribute("books", availableBooks);
        return "allBooks";
    }

    @PostMapping("/submitReturnBook")
    public String submitReturnBook(@Valid @ModelAttribute("returning") Returning returning, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(returning);
            return "returnBook";
        } else {
            System.out.println(returning);
            model.addAttribute("message", "Return successful!");
            return "studentHomePage";
        }
    }

    @PostMapping("/submitRenewBook")
    public String submitRenewBook(@Valid @ModelAttribute("renew") Renew renew, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(renew);
            return "renewBook";
        } else {
            System.out.println(renew);
            model.addAttribute("message", "Renew successful!");
            return "studentHomePage";
        }
    }

    @PostMapping("/submitLendBook")
    public String submitLendBook(@Valid @ModelAttribute("lend") Lend lend, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "lendBook";
        } else {
            System.out.println(lend);
            // lendDetailsService.createLendDetails(lend);
            model.addAttribute("message", "Lending successful!");
            return "studentHomePage";
        }
    }

    // @PostMapping("/lendtable/{id}")
    // public String lendBook(@PathVariable Integer id) {
    // booksService.createBook(id);
    // return "redirect:/studentHomePage";
    // }

    @PostMapping("/submitFineDetails")
    public String submitFineDetails(@Valid @ModelAttribute("finedet") FineDetails finedet, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "fineDetails";
        } else {
            System.out.println(finedet);
            model.addAttribute("message", "successful!");
            return "studentHomePage";
        }
    }

    // @PostMapping("/addLendDetails")
    // public ResponseEntity<LendDetails> addLendDetails(@RequestBody LendDetails
    // lendDetails) {
    // LendDetails savedLendDetails =
    // lendDetailsService.createLendDetails(lendDetails);
    // System.out.println("req sent");
    // return ResponseEntity.ok(savedLendDetails);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<LendDetails> updateLendDetails(@PathVariable Integer
    // id, @RequestBody LendDetails lendDetailsDetails) {
    // LendDetails updatedLendDetails = lendDetailsService.updateLendDetails(id,
    // lendDetailsDetails);
    // System.out.println("req sent");
    // return ResponseEntity.ok(updatedLendDetails);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteLendDetails(@PathVariable Integer id) {
    // lendDetailsService.deleteLendDetails(id);
    // System.out.println("req sent");
    // return ResponseEntity.noContent().build();
    // }

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

    @GetMapping("/updateBook")
    public String showUpdateBookPage(Model model) {
        UpdateBook updateBook = new UpdateBook();
        model.addAttribute(updateBook);
        return "updateBook";
    }

    @GetMapping("/deleteBook")
    public String showDeleteBookPage(Model model) {
        Books deleteBook = new Books();
        model.addAttribute(deleteBook);
        return "deleteBook";
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

    @PostMapping("/submitAddBook")
    public String submitAddBook(@Valid @ModelAttribute("addBook") Books addBook, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "bookManagement";
        } else {
            System.out.println(addBook);
            booksService.createBook(addBook);
            model.addAttribute("message", "successful!");
            return "librarianHomePage";
        }
    }

    @PostMapping("/submitUpdateBook")
    public String submitUpdateBook(@Valid @ModelAttribute("updateBook") UpdateBook updateBook,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(updateBook);
            return "updateBook";
        } else {
            System.out.println(updateBook);
            model.addAttribute("message", "successful!");
            return "librarianHomePage";
        }
    }

    @PostMapping("/submitDeleteBook")
    public String submitDeleteBook(@Valid @ModelAttribute("deleteBook") Books deleteBook,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "deleteBook";
        } else {
            System.out.println(deleteBook);
            booksService.deleteBook(deleteBook.getBookid());
            model.addAttribute("message", "successful!");
            return "librarianHomePage";
        }
    }

    // @GetMapping("/GetAllBooks")
    // public List<Books> getAllBooks(){
    // System.out.println("req sent");
    // return booksService.getAllBooks();
    // }

    // @PostMapping("/books")
    // public ResponseEntity<Books> createBook(Books book) {
    // Books savedBook = booksService.createBook(book);
    // System.out.println("req sent");
    // return ResponseEntity.ok(savedBook);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Books> updateBook(@PathVariable Integer id,
    // @RequestBody Books bookDetails) {
    // Books updatedBook = booksService.updateBook(id, bookDetails);
    // System.out.println("req sent");
    // return ResponseEntity.ok(updatedBook);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
    // booksService.deleteBook(id);
    // System.out.println("req sent");
    // return ResponseEntity.noContent().build();
    // }

    @GetMapping("/search")
    public String searchForm(Model model) {
        return "search";
    }

    @PostMapping("/search")
    public String searchResults(@RequestParam(value = "author", required = false) String authors,
            @RequestParam(value = "bookName", required = false) String bookNames,
            @RequestParam(value = "subject", required = false) String subjects,
            Model model) {

        List<Books> books = booksService.getAllBooks();

        // Null checks for the lists
        if (authors != null && !authors.isEmpty()) {
            books = books.stream().filter(book -> authors.contains(book.getAuthor())).collect(Collectors.toList());
        }
        if (bookNames != null && !bookNames.isEmpty()) {
            books = books.stream().filter(book -> bookNames.contains(book.getBookname())).collect(Collectors.toList());
        }
        if (subjects != null && !subjects.isEmpty()) {
            books = books.stream().filter(book -> subjects.contains(book.getSubject())).collect(Collectors.toList());
        }

        model.addAttribute("books", books);
        System.out.println(books);
        return "searchResults";
    }

}
