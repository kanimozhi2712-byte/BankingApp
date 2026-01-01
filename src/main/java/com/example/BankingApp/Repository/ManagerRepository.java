package com.example.BankingApp.Repository;


import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Integer> {
    Optional<Manager> findByName(String name);

}
