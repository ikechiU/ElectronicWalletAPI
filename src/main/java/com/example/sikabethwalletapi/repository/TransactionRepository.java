package com.example.sikabethwalletapi.repository;

import com.example.sikabethwalletapi.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:45
 * @project SikabethWalletAPI
 */

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    Optional<Transaction> findByUuid(String uuid);
    Optional<Transaction> findByFrom(String email);
    Optional<Transaction> findByTransactionReference(String transaction_reference);
}