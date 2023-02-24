package com.example.sikabethwalletapi.pojo.paystack.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 04:18
 * @project SikabethWalletAPI
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetUpTransactionRequest {
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "amount should be specified in Kobo, pesewas or cents")
    private String amount;
    private String callback_url;
    private Object metadata;
    private String currency;
}
