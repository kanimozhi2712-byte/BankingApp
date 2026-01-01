package com.example.BankingApp.Service;

import com.example.BankingApp.Entity.Account;
import com.example.BankingApp.Enum.ApprovalStatus;
import com.example.BankingApp.Enum.CardType;
import com.example.BankingApp.Repository.AccountRepo;
import com.example.BankingApp.DTO.CardRequestDTO;
import com.example.BankingApp.Entity.Card;
import com.example.BankingApp.Repository.AccountRepo;
import com.example.BankingApp.Repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    // Get all pending cards
    public List<Card> getPendingCards() {
        return cardRepository.findByStatus("PENDING");
    }

    // Get card by ID
    public Card getCardById(Integer id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
    }

    // Final approve
    public void approveCard(Integer id) {

        Card card = getCardById(id);

        card.setCardNumber(generateCardNumber());
        card.setCvv(generateCVV());
        card.setExpiryDate(LocalDate.now().plusYears(5));
        card.setStatus("APPROVED");

        cardRepository.save(card);
    }

    // Reject
    public void rejectCard(Integer id) {
        Card card = getCardById(id);
        card.setStatus("REJECTED");
        cardRepository.save(card);
    }

    // Helpers
    private String generateCardNumber() {
        return "4111" + (int)(Math.random() * 1000000000L);
    }

    private String generateCVV() {
        return String.valueOf((int)(Math.random() * 900 + 100));
    }

//    ===================================CREDIT CARD =================================================================================

        public List<Card> getPendingCreditCards() {
            return cardRepository.findByStatusAndCardType("PENDING", "CREDIT");
        }

        public Card getById(Integer id) {
            return cardRepository.findById(id).orElseThrow();
        }

        public void approveCreditCard(Integer id, Double limit) {

            Card card = cardRepository.findById(id).orElseThrow();

            card.setCardNumber("4111" + (int)(Math.random()*1000000000));
            card.setCvv(String.valueOf((int)(Math.random()*900) + 100));
            card.setExpiryDate(LocalDate.now().plusYears(5));
            card.setStatus("APPROVED");

            cardRepository.save(card);
        }
    }


