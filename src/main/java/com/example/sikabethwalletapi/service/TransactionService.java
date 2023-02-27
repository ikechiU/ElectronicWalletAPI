package com.example.sikabethwalletapi.service;

import com.example.sikabethwalletapi.pojo.wallet.response.WalletTransactionResponse;

import java.security.Principal;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 27/02/2023 - 02:09
 * @project SikabethWalletAPI
 */


public interface TransactionService {
    WalletTransactionResponse fetchTransaction(Principal principal, String id);
    List<WalletTransactionResponse> fetchTransactions(Principal principal, int page, int limit);

    List<WalletTransactionResponse> fetchTransactionsByAdmin(Principal principal, int page, int limit);
    List<WalletTransactionResponse> fetchTransactions(Principal principal, int page, int limit, String transactionType);
}
