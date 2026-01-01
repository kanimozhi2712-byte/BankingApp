package com.example.BankingApp.Entity;

import com.example.BankingApp.Enum.ApprovalStatus;
import com.example.BankingApp.Enum.CardType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cardNumber;
    private String cvv;
    private LocalDate expiryDate;

    private String cardType; // CREDIT / DEBIT
    private String status;   // PENDING / APPROVED / REJECTED

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @PrePersist
    public void prePersist() {
        status = "PENDING";
    }
}

