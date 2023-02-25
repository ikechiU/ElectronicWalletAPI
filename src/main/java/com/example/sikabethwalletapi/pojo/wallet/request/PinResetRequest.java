package com.example.sikabethwalletapi.pojo.wallet.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Ikechi Ucheagwu
 * @created 25/02/2023 - 15:38
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinResetRequest {
    @NotBlank(message = "oldPin must be balance")
    private String oldPin;
    @NotBlank(message = "newPin must be balance")
    private String newPin;
    @NotBlank(message = "confirmNewPin must be balance")
    private String confirmNewPin;
}
