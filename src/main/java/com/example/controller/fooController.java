package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
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

    public fooController(UserService userService, LendDetailsService lendDetailsService, BooksService booksService){
        this.userService = userService;
        this.lendDetailsService = lendDetailsService;
        this.booksService = booksService;
    }

    @GetMapping({"/", "/logout"})
    public String getHomePage(){
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
    public String submitRegistration(@Valid @ModelAttribute("user") User user , BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        else{
        System.out.println(user);
        userService.saveUser(user);
        model.addAttribute("message", "Registration successful!");
        String role = user.getRole(); // Assume User has a getRole() method
        return "redirect:/home?role=" + role;}


    }
    @PostMapping("/submitLogin")
    public String submitLogin(@Valid @ModelAttribute("loginuser") LoginUser loginuser, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "login";
        }
        else{
            System.out.println(loginuser);
            userService.getUserById(loginuser.getId());
            model.addAttribute("message", "Login successful!");
            String role = loginuser.getRole(); 
            return "redirect:/home?role=" + role;
        }
    }


    // @GetMapping("/GetAllUsers")
    // public List<UserEntity> getAllUsers(){
    //     System.out.println("req sent");
    //     return userService.getAllUsers();
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
    //     Optional<UserEntity> user = Optional.ofNullable(userService.getUserById(id));
    //     return user.map(ResponseEntity::ok)
    //                .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    // @PostMapping("/users")
    // public ResponseEntity<UserEntity> createUser(UserEntity user) {
    //     UserEntity savedUser = userService.createUser(user);
    //     System.out.println("req sent");
    //     return ResponseEntity.ok(savedUser);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<UserEntity> getUserByIdMethod(@PathVariable Integer id) {
    //     UserEntity user = userService.getUserById(id);
    //     System.out.println("req sent");
    //     return ResponseEntity.ok(user);
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

    @GetMapping("/renewbook")
    public String showRenewBookPage(Model model) {
        Renew renew = new Renew();
        model.addAttribute(renew);
        return "renewBook";
    }
    
    @GetMapping("/fineDetails")
    public String showFineDetailsPage() {
        return "fineDetails";
    }


    
    @PostMapping("/submitReturnBook")
    public String submitReturnBook(@Valid @ModelAttribute("returning") Returning returning, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            System.out.println(returning);
            return "returnBook";
        }
        else{
        System.out.println(returning);
        model.addAttribute("message", "Return successful!");
        return "studentHomePage";}
    } 

    @PostMapping("/submitRenewBook")
    public String submitRenewBook(@Valid @ModelAttribute("renew") Renew renew, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            System.out.println(renew);
            return "renewBook";
        }
        else{
        System.out.println(renew);
        model.addAttribute("message", "Renew successful!");
        return "studentHomePage";}
}    

    @PostMapping("/submitLendBook")
    public String submitLendBook(@Valid @ModelAttribute("lend") Lend lend, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "lendBook";
        }
        else{
        System.out.println(lend);
        // lendDetailsService.createLendDetails(lend);
        model.addAttribute("message", "Lending successful!");
        return "studentHomePage";}
    }

    @PostMapping("/submitFineDetails")
    public String submitFineDetails(@Valid @ModelAttribute("finedet") FineDetails finedet, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "fineDetails";
        }
        else{
        System.out.println(finedet);
        model.addAttribute("message", "successful!");
        return "studentHomePage";}
    }

   

    
    // @PostMapping("/addLendDetails")
    // public ResponseEntity<LendDetails> addLendDetails(@RequestBody LendDetails lendDetails) {
    //     LendDetails savedLendDetails = lendDetailsService.createLendDetails(lendDetails);
    //     System.out.println("req sent");
    //     return ResponseEntity.ok(savedLendDetails);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<LendDetails> updateLendDetails(@PathVariable Integer id, @RequestBody LendDetails lendDetailsDetails) {
    //     LendDetails updatedLendDetails = lendDetailsService.updateLendDetails(id, lendDetailsDetails);
    //     System.out.println("req sent");
    //     return ResponseEntity.ok(updatedLendDetails);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteLendDetails(@PathVariable Integer id) {
    //     lendDetailsService.deleteLendDetails(id);
    //     System.out.println("req sent");
    //     return ResponseEntity.noContent().build();
    // }


    @GetMapping("/bookManagement")
    public String showBookManagementPage(Model model) {
        AddBook addBook = new AddBook();
        model.addAttribute(addBook);
        return "bookManagement";
    }
    @GetMapping("/addBook")
    public String showAddBookPage(Model model){
        AddBook addBook = new AddBook();
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
        DeleteBook deleteBook = new DeleteBook();
        model.addAttribute(deleteBook);
        return "deleteBook";
    }


    @PostMapping("/submitAddBook")
    public String submitAddBook(@Valid @ModelAttribute("addBook") AddBook addBook, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "bookManagement";
        }
        else{
        System.out.println(addBook);
        model.addAttribute("message", "successful!");
        return "librarianHomePage";}
    }

    @PostMapping("/submitUpdateBook")
    public String submitUpdateBook(@Valid @ModelAttribute("updateBook") UpdateBook updateBook, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            System.out.println(updateBook);
            return "updateBook";
        }
        else{
        System.out.println(updateBook);
        model.addAttribute("message", "successful!");
        return "librarianHomePage";}
    }

    @PostMapping("/submitDeleteBook")
    public String submitDeleteBook(@Valid @ModelAttribute("deleteBook") DeleteBook deleteBook, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "deleteBook";
        }
        else{
        System.out.println(deleteBook);
        model.addAttribute("message", "successful!");
        return "librarianHomePage";}
    }


    // @GetMapping("/GetAllBooks")
    // public List<Books> getAllBooks(){
    //     System.out.println("req sent");
    //     return booksService.getAllBooks();
    // }

    // @PostMapping("/books")
    // public ResponseEntity<Books> createBook(Books book) {
    //     Books savedBook = booksService.createBook(book);
    //     System.out.println("req sent");
    //     return ResponseEntity.ok(savedBook);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Books> updateBook(@PathVariable Integer id, @RequestBody Books bookDetails) {
    //     Books updatedBook = booksService.updateBook(id, bookDetails);
    //     System.out.println("req sent");
    //     return ResponseEntity.ok(updatedBook);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
    //     booksService.deleteBook(id);
    //     System.out.println("req sent");
    //     return ResponseEntity.noContent().build();
    // }


}
