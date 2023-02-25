package com.example.sikabethwalletapi.pojo.paystack.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 17:06
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    @Schema(example = "balance")
    @NotBlank(message = "source must be balance")
    private String source;
    @NotBlank(message = "Reason is mandatory")
    @Schema(example = "Gift")
    private String reason;
    @NotBlank(message = "Amount is mandatory")
    @Schema(example = "Amount should be in Kobo, Pesewas or Cents")
    private BigDecimal amount;
    @NotBlank(message = "recipient_code is mandatory")
    @Schema(example = "recipient_code")
    private String recipient;
}
