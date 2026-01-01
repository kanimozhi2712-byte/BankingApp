package com.example.BankingApp.Entity;

import com.example.BankingApp.Enum.TransType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private  String name;
    private  String transid;
    @Enumerated(EnumType.STRING)
    @Column(name = "trans_type", length = 30)
    private TransType transType;
    private  Double transamt;
    private  Double balance;
    private LocalDateTime date;
    private String message;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
