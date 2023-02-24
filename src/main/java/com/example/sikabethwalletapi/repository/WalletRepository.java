package com.example.sikabethwalletapi.repository;

import com.example.sikabethwalletapi.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:45
 * @project SikabethWalletAPI
 */

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findByUuid(String uuid);
    Optional<Wallet> findByWalletId(String walletId);
    Optional<Wallet> findByWalletIdOrUuid(String uuid, String walletId);
}
