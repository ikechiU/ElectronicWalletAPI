package com.example.sikabethwalletapi.pojo.paystack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 18:46
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaystackCustomer {
    private long id;
    private String email;
    private long integration;
    private String domain;
    private String customer_code;
    private boolean identified;
    private List<String> identifications;
}
