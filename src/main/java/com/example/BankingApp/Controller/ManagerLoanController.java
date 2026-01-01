package com.example.BankingApp.Controller;

import com.example.BankingApp.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ManagerLoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/loan-approvals")
    public String approvals(Model model) {
        model.addAttribute("loans", loanService.getPendingLoans());
        return "loan-approvs";
    }

    @PostMapping("/approve-loan")
    public String approve(@RequestParam Integer loanId) {
        loanService.approveLoan(loanId);
        return "redirect:loan-approvals";
    }

    @GetMapping("/reject-loan/{id}")
    public String rejectLoan(@PathVariable Integer id, RedirectAttributes ra) {

        loanService.rejectLoan(id);
        ra.addFlashAttribute("msg", "loan rejected!");

        return "redirect:/loan-approvals";
    }

}

