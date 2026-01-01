package com.example.BankingApp.Service;

import com.example.BankingApp.DTO.AccountDTO;
import com.example.BankingApp.Entity.Account;
import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Repository.AccountRepo;
import com.example.BankingApp.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerRepository customerRepo;

    public Account createAccount(AccountDTO dto) {

        Customer customer = customerRepo.findById(dto.getCustomer_id())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Account account = new Account();
        account.setAcctype(dto.getAccount_type());
        account.setBalance(dto.getBalance());
        account.setCustomer(customer);
        account.setStatus("PENDING");

        return accountRepo.save(account);
    }

    public List<Account> getPendingAccounts() {
        return accountRepo.findByStatus("PENDING");


    }

    public Account getAccountById(Integer id) {
        return accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account Id not found"));
    }

    public void finalApprove(Integer id, String accNo, String ifsccode) {



        Account account =accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account Id not found"));

        account.setAccNo(accNo);
        account.setIfsccode(ifsccode);
        account.setStatus("APPROVED");

        accountRepo.save(account);
    }


    public void rejectAccount(Integer customerid) {
        Account account = accountRepo.findById(customerid).orElseThrow();
        account.setStatus("REJECTED");
        accountRepo.save(account);
    }

    private String generateAccountNumber() {
        return "ACC" + new Random().nextInt(999999);
    }
}
