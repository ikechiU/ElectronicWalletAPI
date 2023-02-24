package com.example.sikabethwalletapi.pojo.paystack.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 16:16
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String type; //e.g. nuban
    private String name;
    private String account_number;
    private String bank_code;
    private String currency;
}
