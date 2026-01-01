package com.example.BankingApp.DTO;

import com.example.BankingApp.Enum.CardType;
import lombok.Data;

@Data
public class CardRequestDTO {
    private Integer accountId;
    private CardType cardType;
    private Double salary; // for credit card approval
}
