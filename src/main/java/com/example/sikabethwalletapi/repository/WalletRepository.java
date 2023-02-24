package com.example.sikabethwalletapi.repository;

import com.example.sikabethwalletapi.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:45
 * @project SikabethWalletAPI
 */

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
}
