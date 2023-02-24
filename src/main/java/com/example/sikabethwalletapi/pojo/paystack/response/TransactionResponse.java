package com.example.sikabethwalletapi.pojo.paystack.response;

import com.example.sikabethwalletapi.pojo.paystack.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 14:35
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private boolean status;
    private String message;
    private Payment data;
}
