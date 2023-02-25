package com.example.sikabethwalletapi.pojo;

import com.example.sikabethwalletapi.enums.Status;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.pojo.paystack.request.CreateCustomerRequest;
import com.example.sikabethwalletapi.pojo.request.RegisterRequest;
import com.example.sikabethwalletapi.pojo.request.UpdateRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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

    public static CreateCustomerRequest mapFromUser(User user) {
        return CreateCustomerRequest.builder()
                .email(user.getEmail())
                .first_name(user.getFirstName())
                .last_name(user.getLastName())
                .phone(user.getPhoneNumber())
                .build();
    }

    public static User mapUser(User user, UpdateRequest request) {
        String firstName = request.getFirstName() == null || Objects.equals(request.getFirstName().trim(), "") ?
                user.getFirstName() : request.getFirstName();
        String lastName = request.getLastName() == null || Objects.equals(request.getLastName().trim(), "") ?
                user.getLastName() : request.getLastName();
        String country = request.getCountry() == null || Objects.equals(request.getCountry().trim(), "") ?
                user.getCountry() : request.getCountry();
        String state = request.getState() == null || Objects.equals(request.getState().trim(), "") ?
                user.getState() : request.getState();
        String homeAddress = request.getHomeAddress() == null || Objects.equals(request.getHomeAddress().trim(), "") ?
                user.getHomeAddress() : request.getHomeAddress();
        String phoneNumber = request.getPhoneNumber() == null || Objects.equals(request.getPhoneNumber().trim(), "") ?
                user.getPhoneNumber() : request.getPhoneNumber();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCountry(country);
        user.setState(state);
        user.setHomeAddress(homeAddress);
        user.setPhoneNumber(phoneNumber);

        return user;
    }

}
