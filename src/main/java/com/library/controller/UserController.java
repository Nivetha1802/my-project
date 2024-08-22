package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.library.Dto.*;
import com.library.entity.*;
import com.library.service.*;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class UserController implements BaseController<UserEntity>{

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // @GetMapping("/home")
    // public String getHomePage(@RequestParam("role") String role) {
    //     if ("student".equalsIgnoreCase(role) || "teacher".equalsIgnoreCase(role)) {
    //         return "studentHomePage";
    //     } else {
    //         return "librarianHomePage";
    //     }
    // }

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
    public String create(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "signup";
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

    @GetMapping({ "/login", "/" })
    public String showLoginPage(@ModelAttribute("loginuser") LoginUser loginuser) {
        return "login";
    }

    @PostMapping("/submitLogin")
    public String get(@Valid @ModelAttribute("loginuser") LoginUser loginuser,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        } else {
            Optional<UserEntity> optionalUser = userService.authenticate(loginuser.getId(), loginuser.getPassword());
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                session.setAttribute("userId", user.getId());
                redirectAttributes.addFlashAttribute("message", "Login successful!");
                String role = user.getRole();
                if ("student".equalsIgnoreCase(role) || "teacher".equalsIgnoreCase(role)) {
                    return "studentHomePage";
                } else {
                    return "librarianHomePage";
                }
        
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid credentials!");
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/logout")
    public String returnToHomePage() {
        return "login";
    }

    @Override
    public String create(@Valid UserEntity entity, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public String update(@Valid UserEntity entity, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public String delete(@Valid UserEntity entity, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public String get(@Valid UserEntity entity, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            HttpSession session) {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}
