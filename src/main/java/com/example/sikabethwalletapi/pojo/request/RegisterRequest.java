package com.example.sikabethwalletapi.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 22:08
 * @project SikabethWalletAPI
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String country;
    private String state;
    private String homeAddress;
    private String phoneNumber;
}
