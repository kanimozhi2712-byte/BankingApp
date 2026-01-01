package com.example.BankingApp.Service;

import com.example.BankingApp.Entity.Manager;
import com.example.BankingApp.Repository.ManagerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class Managerservice {

    @Autowired
    private ManagerRepository managerRepository;

    public Manager getManagerByEmail(Principal principal) {
        Manager manager = managerRepository.findByName(principal.getName()).stream().findFirst().
                orElseThrow(() -> new RuntimeException("Manager not found"));
        return manager;
    }
}
