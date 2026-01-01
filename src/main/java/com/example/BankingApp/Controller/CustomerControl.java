package com.example.BankingApp.Controller;

import com.example.BankingApp.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerControl {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/accounts")
    public String getAccountsByCustomer(Model model) {

        // TEMP: replace with logged-in customer ID later
        Integer customerId = 1;

        model.addAttribute("accounts",
                customerService.getAccountsByCustomer(customerId));

        return "customer-accounts";
    }
}
