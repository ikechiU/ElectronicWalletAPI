package com.example.sikabethwalletapi.pojo.paystack.response;

import com.example.sikabethwalletapi.pojo.paystack.Pages;
import com.example.sikabethwalletapi.pojo.paystack.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 14:35
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsResponse {
    private boolean status;
    private String message;
    private List<Payment> data;
    private Pages meta;
}
