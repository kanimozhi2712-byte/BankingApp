package com.example.BankingApp.Service;

import com.example.BankingApp.Entity.Account;
import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Repository.AccountRepo;
import com.example.BankingApp.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepo accountRepo;

    public Customer addcustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Account> getAccountsByCustomer(Integer customerId) {
        return accountRepo.findByCustomerId(customerId);
    }

    public Customer getcustomer(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer updatecustomer(int id, Customer customer) {
        Customer cus = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id not found"));

        cus.setPhone(customer.getPhone());
        cus.setAddress(customer.getAddress());
        return customerRepository.save(cus);
    }

    public void deletecustomer(int id) {
        customerRepository.deleteById(id);
    }
}
