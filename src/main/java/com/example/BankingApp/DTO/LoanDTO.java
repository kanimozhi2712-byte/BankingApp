package com.example.BankingApp.DTO;

import com.example.BankingApp.Enum.LoanType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoanDTO {
    private Integer customerId;
    private Double loanAmount;
    private Integer tenureMonths;
}
