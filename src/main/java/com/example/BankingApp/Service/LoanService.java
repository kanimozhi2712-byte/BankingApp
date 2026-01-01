package com.example.BankingApp.Service;

import com.example.BankingApp.Entity.Account;
import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Repository.AccountRepo;
import com.example.BankingApp.DTO.LoanDTO;
import com.example.BankingApp.Entity.Loan;
import com.example.BankingApp.Enum.LoanStatus;
import com.example.BankingApp.Repository.AccountRepo;
import com.example.BankingApp.Repository.CustomerRepository;
import com.example.BankingApp.Repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AccountRepo accountRepo;

    public void applyLoan(LoanDTO dto) {

        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Loan loan = new Loan();
        loan.setLoanAmount(dto.getLoanAmount());
        loan.setTenureMonths(dto.getTenureMonths());
        loan.setInterestRate(10.5); // bank decides
        loan.setStatus(LoanStatus.PENDING);
        loan.setCustomer(customer);

        loanRepo.save(loan);
    }

    public List<Loan> getPendingLoans() {
        return loanRepo.findByStatus(LoanStatus.PENDING);
    }

    public void approveLoan(Integer loanId) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new RuntimeException("Loan already processed");
        }

        loan.setStatus(LoanStatus.APPROVED);

        Customer customer = loan.getCustomer();

        Account account = accountRepo.findByCustomer(customer);

        if (account == null) {
            throw new RuntimeException("Customer account not found");
        }

        Double newBalance = account.getBalance() + loan.getLoanAmount();
        account.setBalance(newBalance);

        accountRepo.save(account);
        loanRepo.save(loan);
    }

    public void rejectLoan(Integer customerid) {
        Loan loan = loanRepo.findById(customerid).orElseThrow();
        loan.setStatus(LoanStatus.REJECTED);
        loanRepo.save(loan);
    }
}
