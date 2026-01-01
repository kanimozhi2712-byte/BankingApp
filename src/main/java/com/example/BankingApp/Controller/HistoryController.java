package com.example.BankingApp.Controller;

import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Entity.History;
import com.example.BankingApp.Entity.User;
import com.example.BankingApp.Enum.Role;
import com.example.BankingApp.Enum.TransType;
import com.example.BankingApp.Repository.CustomerRepository;
import com.example.BankingApp.Repository.HistoryRepo;
import com.example.BankingApp.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class HistoryController {

    private final HistoryRepo historyRepo;
    private final UserRepository userRepo;
    private final CustomerRepository customerRepo;

    @GetMapping("/history")
    public String history(
            @RequestParam(required = false) String type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 5);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Customer customer = (user.getRole() == Role.MANAGER)
                ? null
                : customerRepo.findByName(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Page<History> historyPage =
                getFilteredHistory(type, fromDate, toDate, customer, pageable);

        model.addAttribute("historyList", historyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", historyPage.getTotalPages());

        // send back filters (null on first load)
        model.addAttribute("type", type);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        return "TransHistory";
    }


    private Page<History> getFilteredHistory(
            String type,
            LocalDate fromDate,
            LocalDate toDate,
            Customer customer,
            Pageable pageable) {

        LocalDateTime start = (fromDate != null)
                ? fromDate.atStartOfDay()
                : LocalDateTime.MIN;

        LocalDateTime end = (toDate != null)
                ? toDate.atTime(23, 59, 59)
                : LocalDateTime.MAX;

        List<TransType> types = null;

        if (type != null) {
            switch (type) {
                case "CREDIT":
                    types = List.of(TransType.UPI_DEPOSIT, TransType.BANK_DEPOSIT);
                    break;
                case "DEBIT":
                    types = List.of(TransType.UPI_WITHDRAW, TransType.BANK_WITHDRAW);
                    break;
                case "TRANSFER":
                    types = List.of(TransType.UPI_TRANSFER, TransType.BANK_TRANSFER);
                    break;
            }
        }

        if (customer == null) {
            return (types == null)
                    ? historyRepo.findByDateBetween(start, end, pageable)
                    : historyRepo.findByTransTypeInAndDateBetween(types, start, end, pageable);
        }

        return (types == null)
                ? historyRepo.findByCustomerAndDateBetween(customer, start, end, pageable)
                : historyRepo.findByCustomerAndTransTypeInAndDateBetween(customer, types, start, end, pageable);
    }
}
