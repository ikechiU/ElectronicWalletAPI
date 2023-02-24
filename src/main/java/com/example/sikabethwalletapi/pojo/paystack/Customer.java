package com.example.sikabethwalletapi.pojo.paystack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 13:55
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String customer_code;
    private String phone;
    private String risk_action;
}
