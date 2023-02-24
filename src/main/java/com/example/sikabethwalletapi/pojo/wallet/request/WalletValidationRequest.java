package com.example.sikabethwalletapi.pojo.wallet.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 19:54
 * @project SikabethWalletAPI
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletValidationRequest {
    private String country;
    private String type;
    private String account_number;
    private String bvn;
    private String bank_code;
    private String first_name;
    private String last_name;
    private String customer_code;;
}
