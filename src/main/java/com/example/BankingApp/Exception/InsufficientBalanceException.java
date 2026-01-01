package com.example.BankingApp.Exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message){

        super(message);
    }
}
