package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.library.Dto.*;
import com.library.entity.*;
import com.library.service.*;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/studentHomePage")
    public String getStudentHomePage() {
        return "studentHomePage";
    }

    @GetMapping("/librarianHomePage")
    public String getLibrarianHomePage() {
        return "librarianHomePage";
    }

    @GetMapping("/signup")
    public String showSignupPage(@ModelAttribute("user") User user) {
        return "signupPage";
    }

    @PostMapping("/submitRegistration")
    public String create(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model, String selectedEntities, HttpSession session)
            throws JsonMappingException, JsonProcessingException {
        if (bindingResult.hasErrors()) {
            return "signupPage";
        } else {
            session.setAttribute("userId", user.getId());
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("message", "Registration successful!");
            String role = user.getRole();
            if ("student".equalsIgnoreCase(role) || "teacher".equalsIgnoreCase(role)) {
                        return "studentHomePage";
                    } else {
                        return "librarianHomePage";
                    }
        }

    }

    @GetMapping({ "/login", "/", "/logout" })
    public String showLoginPage(@ModelAttribute("loginuser") LoginUser loginuser) {
        return "loginPage";
    }

    @PostMapping("/submitLogin")
    public String get(@Valid @ModelAttribute("loginuser") LoginUser loginuser,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "loginPage";
        } else {
            Optional<UserEntity> optionalUser = userService.authenticate(loginuser.getId(), loginuser.getPassword());
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                session.setAttribute("userId", user.getId());
                redirectAttributes.addFlashAttribute("message", "Login successful!");
                String role = user.getRole();
                if ("student".equalsIgnoreCase(role) || "teacher".equalsIgnoreCase(role)) {
                    return "redirect:/studentHomePage";
                } else {
                    return "redirect:/librarianHomePage";
                }
        
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid credentials!");
                return "redirect:/login";
            }
        }
    }
}
