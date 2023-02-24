package com.example.sikabethwalletapi.pojo.paystack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 15:32
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    private long id;
    private String bank;
    private String slug;
    private String code;
    private String longcode;
    private String gateway;
    private boolean pay_with_bank;
    private boolean active;
    private boolean is_deleted;
    private String country;
    private String currency;
    private String type;
}
