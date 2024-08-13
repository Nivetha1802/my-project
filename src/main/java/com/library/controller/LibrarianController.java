package com.library.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.library.entity.Books;
import com.library.model.AddBook;
import com.library.model.DeleteBook;
import com.library.model.UpdateBook;
import com.library.service.BooksService;

@Controller
public class LibrarianController {

    private final BooksService booksService;

    public LibrarianController(BooksService booksService) {
        this.booksService = booksService;

    }

    @GetMapping("/bookManagement")
    public String showBookManagementPage(Model model) {
        AddBook addBook = new AddBook();
        model.addAttribute(addBook);
        return "bookManagement";
    }

    @GetMapping("/addBook")
    public String showAddBookPage(Model model) {
        AddBook addBook = new AddBook();
        model.addAttribute(addBook);
        return "bookManagement";
    }

    @PostMapping("/submitAddBook")
    public String submitAddBook(@Valid @ModelAttribute("addBook") Books addBook, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/bookManagement";
        } else {
            booksService.create(addBook);
            redirectAttributes.addFlashAttribute("message", "Successfully Added Books");
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
            RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/updateBook";
        } else {
            booksService.update(updateBook.getBookid(), updateBook);
            redirectAttributes.addFlashAttribute("message", "Successfully Updated Book!");
            return "redirect:/librarianHomePage";
        }
    }

    @GetMapping("/deleteBook")
    public String showDeleteBookPage(Model model) {
        DeleteBook deleteBook = new DeleteBook();
        model.addAttribute(deleteBook);
        return "deleteBook";
    }

    @PostMapping("/submitDeleteBook")
    public String submitDeleteBook(@Valid @ModelAttribute("deleteBook") Books deleteBook,
            RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/deleteBook";
        } else {
            booksService.delete(deleteBook.getBookid());
            redirectAttributes.addFlashAttribute("message", "Successfully Deleted Book!");
            return "redirect:/librarianHomePage";
        }
    }

    @GetMapping("/allBooks")
    public String showAllBookPage(Model model) {
        List<Books> availableBooks = booksService.getAll();
        model.addAttribute("books", availableBooks);
        return "allBooks";
    }
    
    @GetMapping("/getBookDetails")
    public ResponseEntity<Books> getBookDetails(@RequestParam("bookid") Integer bookid) {
        Optional<Books> optionalBook = booksService.getById(bookid);
        if (optionalBook.isPresent()) {
            return new ResponseEntity<>(optionalBook.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}