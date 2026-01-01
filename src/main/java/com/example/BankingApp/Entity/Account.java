package com.example.BankingApp.Entity;

import com.example.BankingApp.Enum.Acctype;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "upi_number", columnDefinition = "INT DEFAULT 0")
    private Integer upiNumber;
    private String accNo;
    private String acctype;
    private Double balance;
    private String ifsccode;
    private String status;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDate.now();
        status = "PENDING"; // DEFAULT
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDate.now();
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

}

