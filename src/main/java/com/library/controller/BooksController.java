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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.library.Dto.AddBook;
import com.library.entity.Books;
import com.library.service.BooksServiceImpl;

@Controller
@RequestMapping("/books")
public class BooksController implements BaseController<Books> {

    private final BooksServiceImpl booksService;

    public BooksController(BooksServiceImpl booksService) {
        this.booksService = booksService;

    }

    @GetMapping("")
    public String showBookManagementPage(Model model) {
        AddBook addBook = new AddBook();
        model.addAttribute(addBook);
        return "bookManagementPage";
    }

    @GetMapping("/add")
    public String showAddBookPage(Model model) {
        Books addBook = new Books();
        model.addAttribute("addBook", addBook);
        return "bookManagementPage";
    }

    @PostMapping("/add")
    public String create(@Valid @ModelAttribute("addBook") Books addBook, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model, @RequestParam(required = false) String selectedEntities, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "bookManagementPage";
        } else {
            booksService.create(addBook);
            redirectAttributes.addFlashAttribute("message", "Successfully Added Books");
            return "redirect:/librarianHome";
        }
    }

    @GetMapping("/update")
    public String showUpdateBookPage(Model model) {
        Books updateBook = new Books();
        model.addAttribute("updateBook", updateBook);
        return "updateBookPage";
    }

    @PostMapping("/update")
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

    @GetMapping("/delete")
    public String showDeleteBookPage(Model model) {
        Books deleteBook = new Books();
        model.addAttribute("deleteBook", deleteBook);
        return "deleteBookPage";
    }

    @PostMapping("/delete")
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
            return "redirect:/delete";
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
    
    @GetMapping("/getBook")
    public ResponseEntity<Books> getBookDetails(@RequestParam("id") Integer id) {
        Optional<Books> optionalBook = booksService.getById(id);
        if (optionalBook.isPresent()) {
            return new ResponseEntity<>(optionalBook.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
