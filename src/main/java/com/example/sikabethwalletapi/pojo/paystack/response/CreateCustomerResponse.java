package com.example.sikabethwalletapi.pojo.paystack.response;

import com.example.sikabethwalletapi.pojo.paystack.PaystackCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 18:45
 * @project SikabethWalletAPI
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateCustomerResponse {
    private boolean status;
    private String message;
    private PaystackCustomer data;
}
