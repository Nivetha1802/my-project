package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class fooController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    String getHomePage(){
    return "/home";
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
    @GetMapping("/home")
    public String showHomePage(Model model) {
        Search search = new Search();
        model.addAttribute(search);
        return "home";
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
    @GetMapping("/bookManagement")
    public String showBookManagementPage() {
        return "bookManagement";
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
    public String submitRegistration(@ModelAttribute("user") User user , BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        System.out.println(user);
        model.addAttribute("message", "Registration successful!");
        return "redirect:/home";
    }
    @PostMapping("/submitLogin")
    public String submitLogin(@ModelAttribute("loginuser") LoginUser loginuser){
        System.out.println(loginuser);
        return "redirect:/home";
    }  

    @PostMapping("/submitReturnBook")
    public String submitReturnBook(@ModelAttribute("returnbook") ReturnBook returnbook){
        System.out.println(returnbook);
        return "redirect:/home";
    } 

    @PostMapping("/submitRenewBook")
    public String submitRenew(@ModelAttribute("renewbook") Renew renew){
        System.out.println(renew);
        return "redirect:/home";
    } 

    @PostMapping("/submitLendBook")
    public String submitRenew(@ModelAttribute("lend") Lend lend){
        System.out.println(lend);
        return "redirect:/home";
    }


    // @PostMapping("/submitLogin")
    // public String submitLogin(@RequestParam("name") String name,
    //         @RequestParam("role") String role,
    //         @RequestParam("idNumber") String id,
    //         @RequestParam("password") String password,
    //         Model model) {
    //     // Save user details to the database (implement this logic)

    //     model.addAttribute("name", name);
    //     model.addAttribute("role", role);
    //     model.addAttribute("idNumber", id);
    //     model.addAttribute("password", password);
    //     System.out.println(model);
    //     return "welcome"; // Redirect to welcome.jsp or any other page
    // }
}
