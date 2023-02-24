package com.example.sikabethwalletapi.pojo.paystack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 17:40
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private long bank_id;
    private String account_number;
    private String account_name;
}