package com.example.BankingApp.Controller;

import com.example.BankingApp.DTO.AccountDTO;
import com.example.BankingApp.DTO.TransDto;
import com.example.BankingApp.Entity.Transaction;
import com.example.BankingApp.Repository.TransRepository;
import com.example.BankingApp.Service.TransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
public class TransactionController {

    @Autowired
    private TransService transService;

    @GetMapping("/transaction")
    public String gettransaction() {
        return "transaction";
    }


    @GetMapping("/credit")
    public String creditdetail() {
        return "credit";
    }

    @GetMapping("/debit")
    public String debitdetail() {
        return "debit";
    }

    @GetMapping("/transfer")
    public String transferdetails() {
        return "transfer";
    }

    @GetMapping("/creditamt")
    public String creditamt(Principal principal,TransDto transDto) {
        transService.creditamt(principal,transDto);
        return "transaction";
    }

    @GetMapping("/debitamt")
    public String debitamt(Principal principal,TransDto transDto) {
        transService.debitamt(principal,transDto);
        return "transaction";
    }

    @GetMapping("/transferamt")
    public String transferamt(Principal principal,TransDto transDto) {
        transService.transferamt(principal,transDto);
        return "transaction";
    }

    @GetMapping("/upi_bank")
    public String upi_bank() {
        return "upi_bank";
    }

    //=====================================================================================================================
    @PostMapping("/upi/credit")
    public String upiCredit(Principal principal,TransDto dto) {
        transService.upiCredit(principal,dto);
        return "Upitransaction";
    }

    @PostMapping("/upi/debit")
    public String upiDebit( Principal principal,TransDto dto) {
        transService.upiDebit(principal,dto);
        return "Upitransaction";
    }

    @GetMapping("/upicreate")
    public String upiCreate(Principal principal) {

        return transService.upiCreate(principal);

    }


    @GetMapping("/upipage")
    public String upierror( ) {

        return "upi";

    }

    @GetMapping("/upiadd")
    public String upiadd(Principal principal,AccountDTO accountDTO ) {

       return  transService.upiadd(principal,accountDTO);
    }


    @GetMapping("/Upicredit")
    public String upicredit() {
        return "Upicredit";
    }

    @GetMapping("/Upidebit")
    public String upidebit() {
        return "Upidebit";
    }

    @GetMapping("/Upitransfer")
    public String upitransfer() {
        return "Upitransfer";
    }


    @GetMapping("/upitransaction")
    public String upitransaction(Principal principal,TransDto dto) {
        return transService.upitransaction(principal,dto);
    }
}