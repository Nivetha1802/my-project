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


    @GetMapping("/studentHome")
    public String getStudentHomePage() {
        return "studentHomePage";
    }

    @GetMapping("/librarianHome")
    public String getLibrarianHomePage() {
        return "librarianHomePage";
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        UserDto user = new UserDto();
        model.addAttribute(user);
        return "signupPage";
    }

    @PostMapping("/register")
    public String create(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model, String selectedEntities, HttpSession session)
            throws JsonMappingException, JsonProcessingException {
        if (bindingResult.hasErrors()) {
            return "signupPage";
        } else {
            session.setAttribute("userId", user.getId());
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("message", "Registration successful!");
            return "redirect:" + getRedirectUrlBasedOnRole(user.getRole());

        }

    }

    @GetMapping({ "", "/", "/logout" })
    public String showLoginPage(@ModelAttribute("loginuser") LoginUser loginuser) {
        return "loginPage";
    }

    @PostMapping("/login")
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
                return "redirect:" + getRedirectUrlBasedOnRole(user.getRole());

        
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid credentials!");
                return "redirect:/login";
            }
        }
    }
    private String getRedirectUrlBasedOnRole(String role) {
        if ("student".equalsIgnoreCase(role) || "teacher".equalsIgnoreCase(role)) {
            return "/studentHome";
        } else {
            return "/librarianHome";
        }
    }
}
