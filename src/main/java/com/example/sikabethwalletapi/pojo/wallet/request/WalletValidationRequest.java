package com.example.sikabethwalletapi.pojo.wallet.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "country must be balance")
    private String country;
    @NotBlank(message = "oldPin must be balance")
    private String type;
    @NotBlank(message = "oldPin must be balance")
    private String account_number;
    @NotBlank(message = "oldPin must be balance")
    private String bvn;
    @NotBlank(message = "oldPin must be balance")
    private String bank_code;
    @NotBlank(message = "oldPin must be balance")
    private String first_name;
    @NotBlank(message = "oldPin must be balance")
    private String last_name;
    @NotBlank(message = "oldPin must be balance")
    private String customer_code;;
}
