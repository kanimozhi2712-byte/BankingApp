package com.example.BankingApp.Service;

import com.example.BankingApp.DTO.AccountDTO;
import com.example.BankingApp.DTO.TransDto;
import com.example.BankingApp.Entity.Account;
import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Entity.History;
import com.example.BankingApp.Entity.Transaction;
import com.example.BankingApp.Enum.TransType;
import com.example.BankingApp.Repository.AccountRepo;
import com.example.BankingApp.Repository.CustomerRepository;
import com.example.BankingApp.Repository.HistoryRepo;
import com.example.BankingApp.Repository.TransRepository;
import org.hibernate.engine.internal.ManagedTypeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransService {

    @Autowired
    private TransRepository transRepo;

    @Autowired
    private CustomerRepository  customerRepo;

    @Autowired
    private AccountRepo accrepo;

    @Autowired
    private HistoryRepo historyRepo;

    // ================= BANK CREDIT =================
    public void creditamt(Principal principal,TransDto dto) {

        Account account = accrepo.findByAccNo(dto.getAccno())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (dto.getAmt() <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + dto.getAmt());

        Transaction tx = new Transaction();
        tx.setAccno(dto.getAccno());
        tx.setAmt(dto.getAmt());
        tx.setTransType(TransType.BANK_DEPOSIT);
        tx.setUpinum(account.getUpiNumber());
        tx.setMessage("Amount credited successfully");
        tx.setCustomer(account.getCustomer());

        accrepo.save(account);
        transRepo.save(tx);

        saveHistory(principal, dto.getAmt(),account.getBalance(),TransType.BANK_DEPOSIT,tx.getMessage(),account);
    }

    // ================= BANK DEBIT =================
    public void debitamt(Principal principal,TransDto dto) {

        Account account = accrepo.findByAccNo(dto.getAccno())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (dto.getAmt() <= 0 || dto.getAmt() > account.getBalance()) {
            throw new RuntimeException("Invalid amount / insufficient balance");
        }

        account.setBalance(account.getBalance() - dto.getAmt());

        Transaction tx = new Transaction();
        tx.setAccno(dto.getAccno());
        tx.setAmt(dto.getAmt());
        tx.setTransType(TransType.BANK_WITHDRAW);
        tx.setUpinum(account.getUpiNumber());
        tx.setMessage("Amount debited successfully");
        tx.setCustomer(account.getCustomer());

        accrepo.save(account);
        transRepo.save(tx);
        saveHistory(principal, dto.getAmt(),account.getBalance(),TransType.BANK_WITHDRAW,tx.getMessage(),account);
    }

    // ================= TRANSFER =================
    public void transferamt(Principal principal,TransDto dto) {

        Account from = accrepo.findByAccNo(dto.getAccno())
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account to = accrepo.findByAccNo(dto.getAccno1())
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (dto.getAmt() <= 0 || dto.getAmt() > from.getBalance()) {
            throw new RuntimeException("Invalid transfer amount");
        }

        from.setBalance(from.getBalance() - dto.getAmt());
        to.setBalance(to.getBalance() + dto.getAmt());

        Transaction tx = new Transaction();
        tx.setAccno(from.getAccNo());
        tx.setAmt(dto.getAmt());
        tx.setTransType(TransType.BANK_TRANSFER);
        tx.setUpinum(from.getUpiNumber());
        tx.setMessage("Amount transferred successfully");
        tx.setCustomer(from.getCustomer());

        accrepo.save(from);
        accrepo.save(to);
        transRepo.save(tx);

        saveHistory(principal, dto.getAmt(),from.getBalance(),TransType.BANK_TRANSFER,tx.getMessage(),from);
    }



    public String upiCreate(Principal principal) {

        Customer customer=customerRepo.findByName(principal.getName()).get();
        Account accounts =accrepo.findByCustomerId(customer.getId()).get(0);
        if(accounts.getUpiNumber()==null ){
            return "redirect:/upipage";
        }

            return "Upitransaction";

    }

    public String upiadd(Principal principal,AccountDTO accountDTO ) {

        Customer customer=customerRepo.findByName(principal.getName()).
                orElseThrow(() -> new RuntimeException("Customer not found"));
        List<Account> accounts = accrepo.findByCustomerId(customer.getId());

        if (accounts.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account account = accounts.get(0);
        account.setUpiNumber(accountDTO.getUpi_number());
        accrepo.save(account);

        return "Upitransaction";

    }

//    =====================================================================================================

    // ================= UPI CREDIT =================
    public void upiCredit(Principal principal,TransDto dto) {

        Account account = accrepo.findByUpiNumber(dto.getUpinum())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("UPI not found"));

        if (dto.getAmt() <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        account.setBalance(account.getBalance() + dto.getAmt());

        Transaction tx = new Transaction();
        tx.setAccno(account.getAccNo());
        tx.setAmt(dto.getAmt());
        tx.setCustomer(account.getCustomer());
        tx.setMessage("UPI credit successful");
        tx.setTransType(TransType.UPI_DEPOSIT);
        tx.setUpinum(dto.getUpinum());
        accrepo.save(account);
        transRepo.save(tx);
        saveHistory(principal, dto.getAmt(),account.getBalance(),TransType.UPI_DEPOSIT,tx.getMessage(),account);
    }

    // ================= UPI DEBIT =================
    public void upiDebit(Principal principal,TransDto dto) {

        Account account = accrepo.findByUpiNumber(dto.getUpinum())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("UPI not found"));

        if (dto.getAmt() <= 0 || dto.getAmt() > account.getBalance()) {
            throw new RuntimeException("Invalid amount");
        }

        account.setBalance(account.getBalance() - dto.getAmt());

        Transaction tx = new Transaction();
        tx.setAccno(account.getAccNo());
        tx.setAmt(dto.getAmt());
        tx.setCustomer(account.getCustomer());
        tx.setMessage("UPI Debit successful");
        tx.setTransType(TransType.UPI_WITHDRAW);
        tx.setUpinum(dto.getUpinum());
        accrepo.save(account);
        transRepo.save(tx);
        saveHistory(principal, dto.getAmt(),account.getBalance(),TransType.UPI_WITHDRAW,tx.getMessage(),account);
    }

    public String upitransaction(Principal principal,TransDto dto) {

        Account from = accrepo.findByAccNo(dto.getAccno())
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account to = accrepo.findByAccNo(dto.getAccno1())
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("To account not found"));

        System.out.println("Amt :"+dto.getAmt());
        System.out.println("Upi number:"+dto.getUpinum());
        if (dto.getAmt() <= 0 || dto.getAmt() > from.getBalance() || !from.getUpiNumber().equals(dto.getUpinum())) {
            throw new RuntimeException("Invalid transfer amount");
        }

        from.setBalance(from.getBalance() - dto.getAmt());
        to.setBalance(to.getBalance() + dto.getAmt());

        Transaction tx = new Transaction();
        tx.setAccno(from.getAccNo());
        tx.setAmt(dto.getAmt());
        tx.setTransType(TransType.UPI_TRANSFER);
        tx.setUpinum(dto.getUpinum());
        tx.setMessage("Amount transferred successfully");
        tx.setCustomer(from.getCustomer());

        accrepo.save(from);
        accrepo.save(to);
        transRepo.save(tx);
        saveHistory(principal, dto.getAmt(),from.getBalance(),TransType.UPI_TRANSFER,tx.getMessage(),from);

        return "Upitransaction";
    }


    private void saveHistory(Principal principal,Double amt,Double balance,TransType transType,String  message,Account account) {


        History history = new History();
        history.setName(principal.getName());
        int num = (int) (Math.random() * 100000);
        history.setTransid("TSN" + num);
        history.setTransamt(amt);
        history.setBalance(balance);
        history.setTransType(transType);
        history.setDate(LocalDateTime.now());
        history.setMessage(message);
        history.setCustomer(account.getCustomer());
        historyRepo.save(history);
    }



}
