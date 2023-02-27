package com.example.sikabethwalletapi.repository;

import com.example.sikabethwalletapi.enums.TransactionType;
import com.example.sikabethwalletapi.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Optional<Transaction> findByUserUuidAndUuidOrTransactionReference(String userUuid, String uuid, String reference);
    Optional<Transaction> findByUserUuid(String userUuid);
    Page<Transaction> findAllByUserUuid(String userUuid, Pageable pageable);
    Page<Transaction> findAllByUserUuidAndTransactionType(String userUuid, TransactionType transactionType, Pageable pageable);
    Optional<Transaction> findByFrom(String email);
    Optional<Transaction> findByTransactionReference(String transaction_reference);
}