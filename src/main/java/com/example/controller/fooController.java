package com.example.controller;

import java.util.*;

import javax.validation.Valid;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.*;
// import com.example.repository.UserRepository;
import com.example.model.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class fooController {

    // @Autowired
    // private UserRepository userRepository;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    String getHomePage(){
    return "/login";
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
    public String showLoginPage(Model model) {
        LoginUser loginuser = new LoginUser();
        model.addAttribute(loginuser);
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "signup";
    }
    // @GetMapping("/home")
    // public String showHomePage(Model model) {
    //     Search search = new Search();
    //     model.addAttribute(search);
    //     return "home";
    // }
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
    @GetMapping("/bookManagement")
    public String showBookManagementPage() {
        return "bookManagement";
    }
    @GetMapping("/addBook")
    public String showAddBookPage() {
        return "bookManagement";
    }
    @GetMapping("/updateBook")
    public String showUpdateBookPage() {
        return "updateBook";
    }
    @GetMapping("/deleteBook")
    public String showDeleteBookPage() {
        return "deleteBook";
    }
    @GetMapping("/returnBook")
    public String showReturnBookPage(Model model) {
        ReturnBook returnbook = new ReturnBook();
        model.addAttribute(returnbook);
        return "returnBook";
    }
    @GetMapping("/fineDetails")
    public String showFineDetailsPage() {
        return "fineDetails";
    }


    @PostMapping("/submitRegistration")
    public String submitRegistration(@Valid @ModelAttribute("user") User user , BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        else{
        System.out.println(user);
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
        model.addAttribute("message", "Login successful!");
        String role = loginuser.getRole(); // Assume LoginUser has a getRole() method
        return "redirect:/home?role=" + role;}
    }  

    @PostMapping("/submitReturnBook")
    public String submitReturnBook(@ModelAttribute("returnbook") ReturnBook returnbook, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "returnBook";
        }
        else{
        System.out.println(returnbook);
        model.addAttribute("message", "Registration successful!");
        return "redirect:/studentHomePage";}
    } 

    @PostMapping("/submitRenewBook")
    public String submitRenewBook(@ModelAttribute("renew") Renew renew, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "renewBook";
        }
        else{
        System.out.println(renew);
        model.addAttribute("message", "Registration successful!");
        return "redirect:/studentHomePage";}
}    

    @PostMapping("/submitLendBook")
    public String submitLendBook(@ModelAttribute("lend") Lend lend, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "lendBook";
        }
        else{
        System.out.println(lend);
        model.addAttribute("message", "Registration successful!");
        return "redirect:/studentHomePage";}
    }


    @RequestMapping("/form")
    public String formPage(Model model){
        Student student = new Student();
        model.addAttribute("student", student);

        List<String> branchList = Arrays.asList("Computer Engineering", "Electrical Engineering", "Mechanical Engineering", "Civil Engineering");
        model.addAttribute("branchList", branchList);
        System.out.println(student);
        return "student-form";
    }

    @PostMapping("/form")
    public String formSubmit(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            List<String> branchList = Arrays.asList("Computer Engineering", "Electrical Engineering", "Mechanical Engineering", "Civil Engineering");
            model.addAttribute("branchList", branchList);
            return "student-form";
        }
        else
            return "registration-success";
    }
}
