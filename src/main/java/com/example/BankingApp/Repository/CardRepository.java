package com.example.BankingApp.Repository;

import com.example.BankingApp.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {

    List<Card> findByStatus(String status);

    List<Card> findByStatusAndCardType(String status, String cardType);
}

