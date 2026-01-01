package com.example.BankingApp.Repository;

import com.example.BankingApp.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransRepository  extends JpaRepository<Transaction, Integer> {

}
