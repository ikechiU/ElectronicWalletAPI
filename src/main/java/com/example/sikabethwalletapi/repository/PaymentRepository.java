package com.example.sikabethwalletapi.repository;

import com.example.sikabethwalletapi.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:45
 * @project SikabethWalletAPI
 */

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByReference(String reference);
    Optional<Payment> findByTransferCode(String transfer_code);
    Optional<Payment> findByTransferCodeOrReference(String transfer_code, String reference);
}