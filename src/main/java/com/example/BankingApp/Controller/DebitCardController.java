package com.example.BankingApp.Controller;

import com.example.BankingApp.Entity.Card;
import com.example.BankingApp.Repository.CardRepository;
import com.example.BankingApp.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DebitCardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Show debit card apply form
    @GetMapping("/apply-debit-card")
    public String showDebitCardForm() {
        return "DebitcardApply";
    }

    // Submit debit card application
    @PostMapping("/apply-debit-card")
    public String applyDebitCard(@RequestParam Integer customerId,
                                 @RequestParam String cardType) {

        Card card = new Card();
        card.setCardType(cardType); // DEBIT
        card.setCustomer(customerRepository.findById(customerId).orElseThrow());

        cardRepository.save(card); // status = PENDING

        return "redirect:/customer";
    }
}

