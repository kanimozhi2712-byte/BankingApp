package com.example.BankingApp.Controller;

import com.example.BankingApp.Entity.Account;
import com.example.BankingApp.Entity.Manager;
import com.example.BankingApp.Service.AccService;
import com.example.BankingApp.Service.Managerservice;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class ManagerController {

    @Autowired
    private AccService accService;

    @Autowired
    private Managerservice managerService;

    @GetMapping
    public String managerDashboard() {
        return "manager-dashboard";
    }


    @GetMapping("/pending-accounts")
    public String pendingAccounts(Model model) {
        model.addAttribute("accounts", accService.getPendingAccounts());
        return "Accpending"; // Thymeleaf page
    }


    @GetMapping("/approve-form/{id}")
    public String showApproveForm(@PathVariable Integer id, Model model) {

        Account account = accService.getAccountById(id);
        model.addAttribute("account", account);

        return "Approval"; // HTML page
    }

    @PostMapping("/approve-submit")
    public String approveSubmit(@RequestParam Integer id,
                                @RequestParam String accNo,
                                @RequestParam String ifsccode) {

        accService.finalApprove(id, accNo, ifsccode);

        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }


    @GetMapping("/details")
    public String managerDetails(Principal principal, Model model) {

        Manager manager = managerService.getManagerByEmail(principal);

        model.addAttribute("manager", manager);
        return "Managerdetails";
    }




}
