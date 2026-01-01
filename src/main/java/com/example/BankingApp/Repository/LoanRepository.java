package com.example.BankingApp.Repository;

import com.example.BankingApp.Entity.Loan;
import com.example.BankingApp.Enum.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByStatus(LoanStatus status);

}
