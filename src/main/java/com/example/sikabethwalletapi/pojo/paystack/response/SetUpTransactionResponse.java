package com.example.sikabethwalletapi.pojo.paystack.response;

import com.example.sikabethwalletapi.pojo.paystack.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 04:39
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpTransactionResponse {
    private boolean status;
    private String message;
    private Transaction data;
}
