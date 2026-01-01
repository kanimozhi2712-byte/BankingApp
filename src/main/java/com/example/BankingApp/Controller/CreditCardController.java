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
public class CreditCardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/apply-credit-card")
    public String showCreditForm() {
        return "CreditApply";
    }

    @PostMapping("/applies-credit-card")
    public String applyCreditCard(@RequestParam Integer customerId) {

        Card card = new Card();
        card.setCardType("CREDIT");
        card.setCustomer(
                customerRepository.findById(customerId).orElseThrow()
        );

        cardRepository.save(card); // status = PENDING

        return "redirect:/customer";
    }
}

