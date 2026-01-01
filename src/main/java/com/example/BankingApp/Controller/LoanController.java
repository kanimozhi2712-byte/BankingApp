package com.example.BankingApp.Controller;

import com.example.BankingApp.DTO.LoanDTO;
import com.example.BankingApp.Entity.Loan;
import com.example.BankingApp.Repository.LoanRepository;
import com.example.BankingApp.Service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/apply")
    public String loanForm() {
        return "loan-apply";
    }

    @PostMapping("/apply-sucess")
    public String submitLoan(LoanDTO dto) {
        loanService.applyLoan(dto);
        return "loan-apply";
    }
}
