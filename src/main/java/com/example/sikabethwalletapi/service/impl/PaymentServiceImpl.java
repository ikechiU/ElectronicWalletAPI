package com.example.sikabethwalletapi.service.impl;

import com.example.sikabethwalletapi.exception.PaymentException;
import com.example.sikabethwalletapi.exception.WalletException;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.model.Wallet;
import com.example.sikabethwalletapi.pojo.paystack.Bank;
import com.example.sikabethwalletapi.pojo.paystack.TransferRecipient;
import com.example.sikabethwalletapi.pojo.paystack.request.AccountRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.CreateCustomerRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.SetUpTransactionRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.TransferRequest;
import com.example.sikabethwalletapi.pojo.paystack.response.*;
import com.example.sikabethwalletapi.pojo.wallet.request.WalletValidationRequest;
import com.example.sikabethwalletapi.repository.WalletRepository;
import com.example.sikabethwalletapi.service.PaymentService;
import com.example.sikabethwalletapi.service.WalletService;
import com.example.sikabethwalletapi.util.AppUtil;
import com.example.sikabethwalletapi.util.AuthDetails;
import com.example.sikabethwalletapi.util.LocalStorage;
import com.example.sikabethwalletapi.util.PayStackHttpEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 04:35
 * @project SikabethWalletAPI
 */

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value(value = "${transfer.recipient}")
    private String TRANSFER_RECIPIENT;
    private final PayStackHttpEntity payStackHttpEntity;
    private final RestTemplate restTemplate;
    private final AppUtil util;
    private final AuthDetails authDetails;
    private final LocalStorage localStorage;
    private final WalletRepository walletRepository;
    private final WalletChecker walletChecker;
    private final PasswordEncoder encoder;

    @Override
    public SetUpTransactionResponse initializeTransaction(Principal principal, SetUpTransactionRequest request) {
        checksBeforeTransaction(principal);

        String url = "https://api.paystack.co/transaction/initialize";
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                payStackHttpEntity.getEntity(request),
                SetUpTransactionResponse.class
        ).getBody();
    }

    @Override
    public VerifyPaymentResponse verifyTransaction(Principal principal, String reference) {
        checksBeforeTransaction(principal);

        String url = "https://api.paystack.co/transaction/verify/" + reference;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                payStackHttpEntity.getEntity(),
                VerifyPaymentResponse.class
        ).getBody();

        //SEND A MAIL TO USER THAT TRANSFER WAS SUCCESSFUL
    }

    @Override
    public TransactionsResponse fetchTransactions(Principal principal) {
        checksBeforeTransaction(principal);

        String url = "https://api.paystack.co/transaction";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                payStackHttpEntity.getEntity(),
                TransactionsResponse.class
        ).getBody();
    }

    @Override
    public TransactionResponse fetchTransaction(Principal principal, long id) {
        authDetails.validateActiveUser(principal);

        String url = "https://api.paystack.co/transaction/" + id;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                payStackHttpEntity.getEntity(),
                TransactionResponse.class
        ).getBody();
    }

    @Override
    public BankResponse fetchBanks(Principal principal, String currency, String type) {
        checksBeforeTransaction(principal);

        String url = "https://api.paystack.co/bank?currency=" + currency + "&type=" + type;
        BankResponse bankResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                payStackHttpEntity.getEntity(),
                BankResponse.class
        ).getBody();

        List<Bank> banks = Objects.requireNonNull(bankResponse).getData().stream().filter(Bank::isActive).toList();
        bankResponse.setData(banks);
        return bankResponse;
    }

    @Override
    public VerifyAccountResponse verifyAccount(Principal principal, String accountNumber, String bankCode) {
        checksBeforeTransaction(principal);

        String url = "https://api.paystack.co/bank/resolve?account_number=" + accountNumber + "&bank_code=" + bankCode;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                payStackHttpEntity.getEntity(),
                VerifyAccountResponse.class
        ).getBody();
    }

    @Override
    public TransferRecipientResponse setUpTransferRecipient(Principal principal, AccountRequest request) {
        User user = checksBeforeTransaction(principal);

        String url = "https://api.paystack.co/transferrecipient";
        TransferRecipientResponse transferRecipientResponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                payStackHttpEntity.getEntity1(request),
                TransferRecipientResponse.class
        ).getBody();
        String reference = util.generateSerialNumber("Tsf") + "Ref";

        TransferRecipient transferRecipient = Objects.requireNonNull(transferRecipientResponse).getData();
        transferRecipient.setReference(reference);
        localStorage.save(TRANSFER_RECIPIENT + user.getEmail(), reference, 3600);
        transferRecipientResponse.setData(transferRecipient);

        return transferRecipientResponse;
    }

    @Override
    public TransferResponse setUpTransfer(Principal principal, TransferRequest request, String reference) {
        User user = checksBeforeTransaction(principal);

        String storedReference = localStorage.getValueByKey(TRANSFER_RECIPIENT + user.getEmail());

        if ((reference == null || storedReference == null) || !reference.equals(storedReference)) {
            throw new PaymentException("Transaction session has expired or reference does not exist. " +
                    "Kindly initiate a new transfer");
        }
        localStorage.clear(TRANSFER_RECIPIENT + user.getEmail());

        String url = "https://api.paystack.co/transfer";
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                payStackHttpEntity.getEntity1(request),
                TransferResponse.class
        ).getBody();
    }

    @Override
    public VerifyTransferResponse verifyTransfer(Principal principal, String reference) {
        checksBeforeTransaction(principal);

        String url = "https://api.paystack.co/transfer/verify/" + reference;
        VerifyTransferResponse response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                payStackHttpEntity.getEntity1(),
                VerifyTransferResponse.class
        ).getBody();

        //SEND A MAIL TO USER THAT TRANSFER WAS SUCCESSFUL
//        Objects.requireNonNull(response).getAmount()
        //STORE TRANSACTION
        return response;
    }

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerRequest request) {
        String url = "https://api.paystack.co/customer";
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                payStackHttpEntity.getEntity(request),
                CreateCustomerResponse.class
        ).getBody();
    }

    @Override
    public CustomerValidationResponse validateCustomer(WalletValidationRequest request) {
        String url = "https://api.paystack.co/customer/" + request.getCustomer_code() + "/identification";
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                payStackHttpEntity.getEntity(request),
                CustomerValidationResponse.class
        ).getBody();
    }

    private User checksBeforeTransaction(Principal principal) {
        User user = authDetails.validateActiveUser(principal);
        Wallet wallet = walletRepository.findByWalletId(user.getWalletId())
                .orElseThrow(() -> new WalletException("Wallet does not exist."));
        walletChecker.checksBeforeTransaction(wallet, encoder);
        return user;
    }

}