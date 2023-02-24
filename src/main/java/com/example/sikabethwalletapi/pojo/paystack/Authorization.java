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
public class Authorization {
    private String authorization_code;
    private String bin;
    private String last4;
    private String exp_month;
    private String exp_year;
    private String channel;
    private String card_type;
    private String bank;
    private String country_code;
    private String brand;
    private String reusable;
    private String signature;
    private String account_name;
}
