package com.example.BankingApp.Entity;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

@ComponentScan("prototype")
@Data
public class ErrorResponse {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;


    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

}
