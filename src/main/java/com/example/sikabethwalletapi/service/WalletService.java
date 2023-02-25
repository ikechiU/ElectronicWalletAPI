package com.example.sikabethwalletapi.service;

import com.example.sikabethwalletapi.SikabethWalletApiApplication;
import com.example.sikabethwalletapi.pojo.paystack.response.VerifyPaymentResponse;
import com.example.sikabethwalletapi.pojo.paystack.response.VerifyTransferResponse;
import com.example.sikabethwalletapi.pojo.wallet.request.InitiateTransferFromSikabethToWalletRequest;
import com.example.sikabethwalletapi.pojo.wallet.request.PinResetRequest;
import com.example.sikabethwalletapi.pojo.wallet.request.SikabethWalletResponse;
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
    WalletResponse transferMoneyWalletToWallet(Principal principal, String recipientWalletId, String amount, String reason, String pin);
    WalletResponse payService(Principal principal, String serviceName, BigDecimal amount, String pin);
    String validateWallet(Principal principal, WalletValidationRequest request);
    String resetPin(Principal principal, PinResetRequest request);
    SikabethWalletResponse initializeTransaction(Principal principal, InitiateTransferFromSikabethToWalletRequest request);
    WalletResponse verifyTransaction(Principal principal, String reference, String transfer_code);
}
