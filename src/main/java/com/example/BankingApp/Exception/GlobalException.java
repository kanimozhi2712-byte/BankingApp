package com.example.BankingApp.Exception;

import com.example.BankingApp.Entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice  //RestController->Rest Ful service to render the text...
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleBalanceException(
            RuntimeException ex) {
        ErrorResponse res=new ErrorResponse();
        res.setTimestamp(LocalDateTime.now());
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setMessage(ex.getMessage());

        return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ex.getMessage());
//    }
}

