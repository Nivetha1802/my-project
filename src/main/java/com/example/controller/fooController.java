package com.example.controller;

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
public class fooController {

    private UserService userService;

    private BooksService booksService;

    private LendDetailsService lendDetailsService;

    private final ObjectMapper objectMapper;

    public fooController(UserService userService, LendDetailsService lendDetailsService, BooksService booksService,
            ObjectMapper objectMapper) {
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

    @GetMapping("/studentHomePage")
    public String getStudentHomePage() {
        return "studentHomePage";
    }

    @GetMapping("/librarianHomePage")
    public String getLibrarianHomePage() {
        return "studentHomePage";
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

    // @GetMapping("/lendBook")
    // public String showLendBookPage(Model model) {
    // Lend lend = new Lend();
    // model.addAttribute(lend);
    // return "lendBook";
    // }

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

    // @PostMapping("/returnBooks")
    // public String returnBooks(@RequestParam("bookIds") List<Integer> bookIds,
    // Model model) {
    // booksService.returnBooks(bookIds);
    // return "redirect:/lend";
    // }

    @PostMapping("/submitlendtable")
    public String submitLendTable(@RequestParam("selectedBooks") String selectedBooks, HttpSession session)
            throws JsonMappingException, JsonProcessingException {
        List<Books> selectedBooksList = objectMapper.readValue(selectedBooks, new TypeReference<List<Books>>() {
        });
        session.setAttribute("selectedBooks", selectedBooksList);
        System.out.println(selectedBooksList);
        return "redirect:/lendDetails";
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
        List<LendDetails> selectedBooksList = objectMapper.readValue(selectedBooks, new TypeReference<List<LendDetails>>() {
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
            HttpSession session) {
        List<Integer> lendIds = Arrays.stream(selectedBooks.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int lendId : lendIds) {
            lendDetailsService.renewBook(lendId);
        }

        session.removeAttribute("selectedBooks");

        return "redirect:/studentHomePage";
    }

    @GetMapping("/fineDetails")
    public String showFineDetailsPage(Model model) {
        List<Books> availableBooks = booksService.getAllBooks();
        System.out.println(booksService.getAllBooks());
        if (availableBooks != null) {
            availableBooks.forEach(book -> System.out.println(book.getBookname())); // Debugging
        }
        model.addAttribute("books", availableBooks);
        return "fineDetails";
    }

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

    @PostMapping("/submitRenewBook")
    public String submitRenewBook(@Valid @ModelAttribute("renew") Renew renew, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(renew);
            return "redirect:/renewBook";
        } else {
            System.out.println(renew);
            model.addAttribute("message", "Renew successful!");
            return "redirect:/studentHomePage";
        }
    }

    @PostMapping("/submitLendBook")
    public String submitLendBook(@Valid @ModelAttribute("lend") Lend lend, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/lendBook";
        } else {
            System.out.println(lend);
            // lendDetailsService.createLendDetails(lend);
            model.addAttribute("message", "Lending successful!");
            return "redirect:/studentHomePage";
        }
    }

    @PostMapping("/submitFineDetails")
    public String submitFineDetails(@Valid @ModelAttribute("finedet") FineDetails finedet, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/fineDetails";
        } else {
            System.out.println(finedet);
            model.addAttribute("message", "successful!");
            return "redirect:/studentHomePage";
        }
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
            return "redirect:/bookManagement";
        } else {
            System.out.println(addBook);
            booksService.createBook(addBook);
            model.addAttribute("message", "successful!");
            return "redirect:/librarianHomePage";
        }
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
