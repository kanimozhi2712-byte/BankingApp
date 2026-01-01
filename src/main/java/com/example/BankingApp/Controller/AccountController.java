package com.example.BankingApp.Controller;


import com.example.BankingApp.DTO.AccountDTO;
import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Service.AccService;
import com.example.BankingApp.Entity.Account;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AccountController {

    @Autowired
    private AccService accservice;




//    @PutMapping("/updateupinumber")
//    public Account createupinumber(@RequestParam int id,@RequestBody AccountDTO accountdto){
//
//        return accservice.createupinumber(id,accountdto);
//
//    }
@GetMapping("/customer/account")
public String createAccount(@ModelAttribute AccountDTO dto) {
    accservice.createAccount(dto);
    return "Customer";
}


//    @GetMapping("/getaccount")
//    public Account getAccountById(@RequestParam int id){
//
//        return accservice.getAccountById(id);
//    }
//
//    @DeleteMapping("deleteaccount")
//    public  String deleteAccount(@RequestParam int id){
//        return  accservice.deleteAccount(id);
//
//    }


}
