package com.example.sikabethwalletapi.pojo.paystack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 25/02/2023 - 14:05
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyTransfer {
    private long id;
    private long integration;
    private Recipient recipient;
    private String description;
    private String metadata;
    private String recipient_code;
    private boolean active;
    private String email;
    private String createdAt;
    private String updatedAt;
}
