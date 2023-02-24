package com.example.sikabethwalletapi.pojo.dto;

import com.example.sikabethwalletapi.enums.Status;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.pojo.request.RegisterRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 22:08
 * @project SikabethWalletAPI
 */


public class Mapper implements Serializable {

    public static User mapUser(RegisterRequest request) {
        return User.builder()
                .uuid(UUID.randomUUID().toString())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .status(Status.INACTIVE)
                .encryptedPassword(new BCryptPasswordEncoder().encode(request.getPassword()))
                .lastLoginDate(new Date())
                .country(request.getCountry())
                .state(request.getState())
                .homeAddress(request.getHomeAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

}
