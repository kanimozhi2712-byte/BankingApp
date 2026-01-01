package com.example.BankingApp.Entity;

import com.example.BankingApp.Enum.TransType;
import com.example.BankingApp.Enum.Transstatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private  String accno;
    private  Double amt;
    @Enumerated(EnumType.STRING)
    @Column(name = "trans_type", length = 30)
    private TransType transType;
    private  String message;
    private Integer upinum;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
