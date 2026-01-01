package com.example.BankingApp.Repository;

import com.example.BankingApp.Entity.Account;
import com.example.BankingApp.Entity.Customer;
import com.example.BankingApp.Entity.History;
import com.example.BankingApp.Enum.TransType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface HistoryRepo  extends JpaRepository<History,Integer> {

    Page<History> findByDateBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);

    Page<History> findByTransTypeInAndDateBetween(
            List<TransType> types,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);

    Page<History> findByCustomerAndDateBetween(
            Customer customer,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);

    Page<History> findByCustomerAndTransTypeInAndDateBetween(
            Customer customer,
            List<TransType> types,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);


}
