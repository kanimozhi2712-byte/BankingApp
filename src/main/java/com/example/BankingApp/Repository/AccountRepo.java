package com.example.BankingApp.Repository;

import com.example.BankingApp.Entity.Account;
import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account,Integer> {


    List<Account> findByCustomerId(Integer customerId);

    List<Account> findByStatus(String status);

    Account findByCustomer(Customer customer);

    List<Account> findByAccNo(String accNo);

    List<Account> findByIfsccode(String ifsccode);

    Boolean existsByAccNo(String accNo);

    List<Account> findByUpiNumber(Integer upiNumber);

    List<Account> findByCustomer_IdAndStatus(Integer customerId, String status);

}
