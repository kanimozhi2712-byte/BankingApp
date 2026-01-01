package com.example.BankingApp.Controller;


import com.example.BankingApp.Entity.Card;
import com.example.BankingApp.Repository.CardRepository;
import com.example.BankingApp.Repository.CustomerRepository;
import com.example.BankingApp.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ManagerCardController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    // Show pending cards
    @GetMapping("/cards")
    public String pendingCards(
            Model model) {
        model.addAttribute("cards", cardService.getPendingCards());
        return "Debitpending";
    }

    // Approve card
    @GetMapping("/approve-card/{id}")
    public String approveCard(@PathVariable Integer id, RedirectAttributes ra) {

        cardService.approveCard(id);
        ra.addFlashAttribute("msg", "Card approved successfully!");

        return "redirect:/cards";
    }

    // Reject card
    @GetMapping("/reject-card/{id}")
    public String rejectCard(@PathVariable Integer id, RedirectAttributes ra) {

        cardService.rejectCard(id);
        ra.addFlashAttribute("msg", "Card rejected!");

        return "redirect:/cards";
    }

    @PostMapping("/apply-card")
    public String applyCard(@RequestParam String cardType,
                            @RequestParam Integer customerId) {


        Card card = new Card();
        card.setCardType(cardType);
        card.setCustomer(customerRepository.findById(customerId).get());

        cardRepository.save(card);
        return "redirect:/customer";
    }

//    ==========================================CREDIT CARD========================================================================

    @GetMapping("/credit-card-approvals")
    public String pendingCreditCards(Model model) {
        model.addAttribute("cards", cardService.getPendingCreditCards());
        return "CreditApply";
    }

    @GetMapping("/approve-credit-card/{id}")
    public String showApproveForm(@PathVariable Integer id, Model model) {
        model.addAttribute("card", cardService.getById(id));
        return "ApproveCreditCard";
    }

    @PostMapping("/approve-credit-card")
    public String approve(@RequestParam Integer id,
                          @RequestParam Double limit) {

        cardService.approveCreditCard(id, limit);
        return "CreditCardSuccess";
    }


}

