package com.example.BankingApp.Entity;

import com.example.BankingApp.Enum.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;
    private String name;
    private String email;
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy="customer")
    @JsonBackReference
    private List<Account> accounts;

}
