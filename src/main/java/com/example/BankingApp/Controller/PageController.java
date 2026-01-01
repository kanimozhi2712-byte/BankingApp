package com.example.BankingApp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    @GetMapping("/page")
    public String login() {
        return "index";   // login.html
    }

    @GetMapping("/manager")
    public String adminDashboard() {
        return "Manager";
    }

    @GetMapping("/customer")
    public String customerDashboard() {
        return "Customer";
    }


    @GetMapping("/customer/accountform")
    public String accountForm() {

    return "Accountform";
    }

}
