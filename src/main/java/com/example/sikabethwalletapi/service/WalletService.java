package com.example.sikabethwalletapi.service;

import com.example.sikabethwalletapi.pojo.wallet.request.WalletValidationRequest;
import com.example.sikabethwalletapi.pojo.wallet.response.WalletResponse;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 18:39
 * @project SikabethWalletAPI
 */


public interface WalletService {
    WalletResponse getWallet(Principal principal);
    List<WalletResponse> getWallets(Principal principal, int page, int limit);
    WalletResponse transferMoney(Principal principal, String recipientWalletId, BigDecimal amount, String pin);
    WalletResponse payService(Principal principal, String serviceName, BigDecimal amount, String pin);
    String validateWallet(Principal principal, WalletValidationRequest request);
}
