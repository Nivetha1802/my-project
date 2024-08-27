package com.library.controller;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
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
import com.library.Dto.AddBook;
import com.library.entity.Books;
import com.library.service.BooksServiceImpl;

@Controller
public class BooksController implements BaseController<Books> {

    private final BooksServiceImpl booksService;

    public BooksController(BooksServiceImpl booksService) {
        this.booksService = booksService;

    }

    @GetMapping("/bookManagement")
    public String showBookManagementPage(Model model) {
        AddBook addBook = new AddBook();
        model.addAttribute(addBook);
        return "bookManagementPage";
    }

    @GetMapping("/addBook")
    public String showAddBookPage(Model model) {
        Books addBook = new Books();
        model.addAttribute("addBook", addBook);
        return "bookManagementPage";
    }

    @PostMapping("/submitAddBook")
    public String create(@Valid @ModelAttribute("addBook") Books addBook, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model, @RequestParam(required = false) String selectedEntities, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "bookManagementPage";
        } else {
            booksService.create(addBook);
            redirectAttributes.addFlashAttribute("message", "Successfully Added Books");
            return "redirect:/librarianHomePage";
        }
    }

    @GetMapping("/updateBook")
    public String showUpdateBookPage(Model model) {
        Books updateBook = new Books();
        model.addAttribute("updateBook", updateBook);
        return "updateBookPage";
    }

    @PostMapping("/submitUpdateBook")
    public String update(@Valid @ModelAttribute("updateBook") Books updateBook, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model, @RequestParam(required = false) String selectedEntities) {
        if (bindingResult.hasErrors()) {
            return "updateBookPage";
        } else {
            booksService.update(updateBook.getId(), updateBook);
            redirectAttributes.addFlashAttribute("message", "Successfully Updated Book!");
            return "redirect:/librarianHomePage";
        }
    }

    @GetMapping("/deleteBook")
    public String showDeleteBookPage(Model model) {
        Books deleteBook = new Books();
        model.addAttribute("deleteBook", deleteBook);
        return "deleteBookPage";
    }

    @PostMapping("/submitDeleteBook")
    public String delete(@Valid @ModelAttribute("deleteBook") Books deleteBook, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model, @RequestParam(required = false) String selectedEntities, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "deleteBookPage";
        } else {
        try {
            booksService.delete(deleteBook.getId());
            redirectAttributes.addFlashAttribute("message", "Successfully Deleted Book!");
        } catch (EmptyResultDataAccessException e) {
            redirectAttributes.addFlashAttribute("error", "Book not found!");
            return "redirect:/deleteBook";
        }
        return "redirect:/librarianHomePage";
    }
}

    @GetMapping("/allBooks")
    public String showAllBookPage(Model model) {
        List<Books> availableBooks = booksService.getAll();
        model.addAttribute("books", availableBooks);
        return "allBooksPage";
    }
    
    @GetMapping("/getBookDetails")
    public ResponseEntity<Books> getBookDetails(@RequestParam("id") Integer id) {
        Optional<Books> optionalBook = booksService.getById(id);
        if (optionalBook.isPresent()) {
            return new ResponseEntity<>(optionalBook.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
