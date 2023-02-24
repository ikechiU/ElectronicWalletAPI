package com.example.sikabethwalletapi.pojo.paystack.response;

import com.example.sikabethwalletapi.pojo.paystack.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 17:39
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyAccountResponse {
    private boolean status;
    private String message;
    private Account data;
}
