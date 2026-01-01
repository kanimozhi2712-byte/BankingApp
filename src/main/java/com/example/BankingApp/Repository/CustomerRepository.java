package com.example.BankingApp.Repository;

import com.example.BankingApp.Entity.Account;
import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByName(String name);


}
