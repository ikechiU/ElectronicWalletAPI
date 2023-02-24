package com.example.sikabethwalletapi.pojo.paystack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 04:43
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    String authorization_url;
    String access_code;
    String reference;
}
