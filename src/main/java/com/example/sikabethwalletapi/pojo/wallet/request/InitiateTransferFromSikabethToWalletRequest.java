package com.example.sikabethwalletapi.pojo.wallet.request;

import com.example.sikabethwalletapi.pojo.paystack.request.SetUpTransactionRequest;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class InitiateTransferFromSikabethToWalletRequest {
    @Schema(example = "Amount should be in Kobo, Pesewas or Cents")
    @NotBlank(message = "amount should be specified in Kobo, pesewas or cents")
    private String amount;


    public static SetUpTransactionRequest mapToSetUpTransactionRequest(String amount, String email) {
        return SetUpTransactionRequest.builder()
                .email(email)
                .amount(amount)
                .currency("NGN")
                .build();
    }
}
