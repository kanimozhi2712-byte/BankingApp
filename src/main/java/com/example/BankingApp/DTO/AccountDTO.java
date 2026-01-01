package com.example.BankingApp.DTO;

import com.example.BankingApp.Enum.Acctype;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String acc_no;
    private String Bank_name;
    @NotNull(message = "Amount cannot be null")
    private Double balance;
    private Integer upi_number;
   private String ifsc_code;
   private String account_type;
   private Integer customer_id;

}
