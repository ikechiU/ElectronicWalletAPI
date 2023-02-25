package com.example.sikabethwalletapi.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 22:08
 * @project SikabethWalletAPI
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivationRequest {
    @NotBlank(message = "email must be balance")
    private String email;
    @NotBlank(message = "token must be balance")
    private String token;
}
