package com.example.sikabethwalletapi.service;

import com.example.sikabethwalletapi.pojo.paystack.request.AccountRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.CreateCustomerRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.SetUpTransactionRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.TransferRequest;
import com.example.sikabethwalletapi.pojo.paystack.response.*;
import com.example.sikabethwalletapi.pojo.wallet.request.WalletValidationRequest;

import java.security.Principal;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 04:34
 * @project SikabethWalletAPI
 */

public interface PaymentService {
    SetUpTransactionResponse initializeTransaction(Principal principal, SetUpTransactionRequest request);
    VerifyPaymentResponse verifyTransaction(Principal principal, String reference);
    TransactionsResponse fetchTransactions(Principal principal);
    TransactionResponse fetchTransaction(Principal principal, long id);
    BankResponse fetchBanks(Principal principal, String currency, String type);
    VerifyAccountResponse verifyAccount(Principal principal, String accountNumber, String bankCode);
    TransferRecipientResponse setUpTransferRecipient(Principal principal, AccountRequest request);
    TransferResponse setUpTransfer(Principal principal, TransferRequest request, String reference);
    VerifyTransferResponse verifyTransfer(Principal principal, String reference);
    CreateCustomerResponse createCustomer(CreateCustomerRequest request);
    CustomerValidationResponse validateCustomer(WalletValidationRequest request);
}
