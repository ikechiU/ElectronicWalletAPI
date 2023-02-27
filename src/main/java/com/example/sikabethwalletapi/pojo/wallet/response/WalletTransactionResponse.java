package com.example.sikabethwalletapi.pojo.wallet.response;

import com.example.sikabethwalletapi.enums.TransactionSource;
import com.example.sikabethwalletapi.enums.TransactionType;
import com.example.sikabethwalletapi.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 19:20
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class WalletTransactionResponse {
    private String uuid;
    private String userUuid;
    private String reason;
    private String from;
    private String to;
    private BigDecimal amount;
    private BigDecimal balance;
    private String transactionReference;
    private TransactionType transactionType;
    private TransactionSource transactionSource;

    public static WalletTransactionResponse mapFromTransaction(Transaction transaction) {
        return WalletTransactionResponse.builder()
                .uuid(transaction.getUuid())
                .userUuid(transaction.getUserUuid())
                .reason(transaction.getReason())
                .from(transaction.getFrom())
                .to(transaction.getTo())
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .transactionReference(transaction.getTransactionReference())
                .transactionType(transaction.getTransactionType())
                .transactionSource(transaction.getTransactionSource())
                .build();
    }

    public static List<WalletTransactionResponse> mapFromTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .map(WalletTransactionResponse::mapFromTransaction)
                .collect(Collectors.toList());
    }
}
