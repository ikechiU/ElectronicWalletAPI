package com.example.sikabethwalletapi.pojo.paystack.response;

import com.example.sikabethwalletapi.pojo.paystack.VerifyTransfer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 25/02/2023 - 14:06
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyTransferResponse {
    private boolean status;
    private String message;
    private long id;
    private VerifyTransfer data;
    private String domain;
    private BigDecimal amount;
    private String currency;
    private String reference;
    private String source;
    private String source_details;
    private String reason;
    private List<String> failures;
    private String transfer_code;
    private String titan_code;
    private String transferred_at;
    private String createdAt;
    private String updatedAt;
}
