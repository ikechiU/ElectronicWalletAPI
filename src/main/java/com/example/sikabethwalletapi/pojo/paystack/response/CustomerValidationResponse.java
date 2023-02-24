package com.example.sikabethwalletapi.pojo.paystack.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 19:52
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerValidationResponse {
    private boolean status;
    private String message;
}
