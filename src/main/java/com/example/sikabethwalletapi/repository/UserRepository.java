package com.example.sikabethwalletapi.repository;

import com.example.sikabethwalletapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:45
 * @project SikabethWalletAPI
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUuid(String uuid);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
