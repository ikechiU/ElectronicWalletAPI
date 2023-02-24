package com.example.sikabethwalletapi.pojo.paystack.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String source;
    private String reason;
    private BigDecimal amount;
    private String recipient;
}
