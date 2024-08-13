package com.library.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entity.LendDetails;
import com.library.entity.UserEntity;
import com.library.model.GoogleBooks;
import com.library.service.GoogleBooksService;
import com.library.service.LendDetailsService;
import com.library.service.UserService;

@Controller
public class StudentController {

    private final UserService userService;
    private final LendDetailsService lendDetailsService;
    private final GoogleBooksService googleBooksService;
    private final ObjectMapper objectMapper;

    public StudentController(UserService userService, LendDetailsService lendDetailsService,
            GoogleBooksService googleBooksService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.lendDetailsService = lendDetailsService;
        this.googleBooksService = googleBooksService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/lendtable")
    public String showLendtablePage(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            List<GoogleBooks> books = googleBooksService.searchBook(query);
            model.addAttribute("books", books);
            model.addAttribute("query", query);
        }

        return "lend_table";
    }

    @PostMapping("/submitlendtable")
    public String submitLendTable(@RequestParam("selectedBooks") String selectedBooks, HttpSession session)
            throws JsonProcessingException {
        if (selectedBooks == null || selectedBooks.isEmpty()) {
            return "redirect:/lendtable";
        }
        List<GoogleBooks> selectedBooksList = objectMapper.readValue(selectedBooks,
                new TypeReference<List<GoogleBooks>>() {
                });
        session.setAttribute("selectedBooks", selectedBooksList);
        return "redirect:/lendDetails";
    }

    @GetMapping("/lendDetails")
    public String showLendDetails(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<GoogleBooks> selectedBooks = (List<GoogleBooks>) session.getAttribute("selectedBooks");
        if (selectedBooks != null) {
            model.addAttribute("selectedBooks", selectedBooks);
        }
        return "lend_details";
    }

    @PostMapping("/submitlenddetails")
    public String submitLendDetail(@RequestParam("selectedBooks") String selectedBooks, HttpSession session,
            RedirectAttributes redirectAttributes) throws JsonMappingException, JsonProcessingException {
        if (selectedBooks == null || selectedBooks.isEmpty()) {
            return "redirect:/lendDetails";
        }
        List<LendDetails> selectedBooksList = objectMapper.readValue(selectedBooks,
                new TypeReference<List<LendDetails>>() {
                });
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "User not logged in!");
            return "redirect:/login";
        }
        Optional<UserEntity> optionalUser = userService.getById(userId);
        if (!optionalUser.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "User not found!");
            return "redirect:/login";
        }
        UserEntity user = optionalUser.get();

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found!");
            return "redirect:/login";
        }
        for (LendDetails book : selectedBooksList) {
            lendDetailsService.processBookLendDetails(book, optionalUser);
        }
        redirectAttributes.addFlashAttribute("message", "Successfully Lent Books!");
        return "redirect:/studentHomePage";
    }

    @GetMapping("/returntable")
    public String showReturnBooksPage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            List<LendDetails> lendBooks = lendDetailsService.getLendDetailsByUserId(userId);
            model.addAttribute("lendBooks", lendBooks);
        }
        return "return_table";
    }

    @PostMapping("/submitReturnBooks")
    public String submitReturnBooks(@RequestParam("selectedBooks") String selectedBooks,
            RedirectAttributes redirectAttributes, HttpSession session) throws JsonProcessingException {
        if (selectedBooks == null || selectedBooks.isEmpty()) {
            return "redirect:/returntable";
        }
        List<LendDetails> selectedBooksList = objectMapper.readValue(selectedBooks,
                new TypeReference<List<LendDetails>>() {
                });
        for (LendDetails book : selectedBooksList) {
            lendDetailsService.delete(book.getLendId());
        }
        session.removeAttribute("selectedBooks");
        redirectAttributes.addFlashAttribute("message", "Successfully Returned Books!");
        return "redirect:/studentHomePage";
    }

    @GetMapping("/renewtable")
    public String showRenewtablePage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            List<LendDetails> lendBooks = lendDetailsService.getLendDetailsByUserId(userId);
            model.addAttribute("lendBooks", lendBooks);
        }
        return "renew_table";
    }

    @PostMapping("/submitRenewtable")
    public String submitRenewtable(@RequestParam("selectedBooks") String selectedBooks,
            RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (selectedBooks == null || selectedBooks.isEmpty()) {
            return "redirect:/renewtable";
        }
        List<LendDetails> selectedBooksList = objectMapper.readValue(selectedBooks,
                new TypeReference<List<LendDetails>>() {
                });
        for (LendDetails book : selectedBooksList) {
            lendDetailsService.renewBook(book.getLendId());
        }
        redirectAttributes.addFlashAttribute("message", "Successfully Renewed Books!");
        return "redirect:/studentHomePage";
    }

    @GetMapping("/fineDetails")
    public String showFineDetailsPage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            Optional<UserEntity> optionalUser = userService.getById(userId);
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                String username = user.getName();
                List<LendDetails> lendBooks = lendDetailsService.getLendDetailsByUserId(userId);
                List<LendDetails> lendBooksWithFine = lendBooks.stream()
                        .filter(lendDetail -> lendDetail.getFine() > 0.0)
                        .collect(Collectors.toList());
                model.addAttribute("books", lendBooksWithFine);
                model.addAttribute("user", username);
            } else {
                
                model.addAttribute("error", "User not found!");
                return "error";
            }
        }
        return "fine_table";
    }
    

    @GetMapping("/search")
    public String search(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            List<GoogleBooks> books = googleBooksService.searchBook(query);
            model.addAttribute("books", books);
            model.addAttribute("query", query);
        }
        return "search";
    }

}
