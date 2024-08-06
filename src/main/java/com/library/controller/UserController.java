package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.library.entity.*;
import com.library.model.*;
import com.library.service.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomePage(@RequestParam("role") String role) {
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
        return "librarianHomePage";
    }

    @GetMapping("/signup")
    public String showSignupPage(@ModelAttribute("user") User user) {
        return "signup";
    }

    @PostMapping("/submitRegistration")
    public String submitRegistrationFormDetails(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,RedirectAttributes redirectAttributes,
             HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "signup";
        } else {
            session.setAttribute("userId", user.getId());
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("message", "Registration successful!");
            String role = user.getRole();
            return "redirect:/home?role=" + role;
        }

    }

    @GetMapping({"/login","/"})
    public String showLoginPage(@ModelAttribute("loginuser") LoginUser loginuser) {
        return "login";
    }

    @PostMapping("/submitLogin")
    public String submitLoginFormDetails(@Valid @ModelAttribute("loginuser") LoginUser loginuser, BindingResult bindingResult,RedirectAttributes redirectAttributes,
             HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        } else {
            UserEntity user = userService.authenticate(loginuser.getId(), loginuser.getPassword());
            if (user != null) {
                session.setAttribute("userId", user.getId());
                redirectAttributes.addFlashAttribute("message", "Login successful!");
                String role = user.getRole();
                return "redirect:/home?role=" + role;
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid credentials!");
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/logout" )
    public String returnToHomePage() {
        return "login";
    }
}
    
   
