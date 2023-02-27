package com.example.sikabethwalletapi.pojo.user.response;

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
public class LoginResponse {
    private String token;
    private String email;

    public static LoginResponse mapFromData(String token, String email) {
        return LoginResponse.builder()
                .token(token)
                .email(email)
                .build();
    }
}
