package com.example.sikabethwalletapi.pojo.paystack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 16:25
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipientDetails {
    private String authorization_code;
    private String account_number;
    private String account_name;
    private String bank_code;
    private String bank_name;
}
