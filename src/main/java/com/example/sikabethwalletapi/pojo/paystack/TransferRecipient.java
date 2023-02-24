package com.example.sikabethwalletapi.pojo.paystack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 16:24
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRecipient {
    private long id;
    private long integration;
    private String name;
    private String recipient_code;
    private boolean active;
    private String currency;
    private String domain;
    private String type;
    private String createdAt;
    private String updatedAt;
    private boolean is_deleted;
    private boolean isDeleted;
    private RecipientDetails details;
}
