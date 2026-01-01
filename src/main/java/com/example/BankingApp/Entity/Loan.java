package com.example.BankingApp.Entity;

import com.example.BankingApp.Enum.LoanStatus;
import com.example.BankingApp.Enum.LoanType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double loanAmount;
    private Integer tenureMonths;
    private Double interestRate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}

