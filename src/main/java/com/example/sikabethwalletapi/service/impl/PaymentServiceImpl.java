package com.example.sikabethwalletapi.service.impl;

import com.example.sikabethwalletapi.exception.PaymentException;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.pojo.paystack.request.AccountRequest;
import com.example.sikabethwalletapi.pojo.paystack.Bank;
import com.example.sikabethwalletapi.pojo.paystack.TransferRecipient;
import com.example.sikabethwalletapi.pojo.paystack.request.CreateCustomerRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.SetUpTransactionRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.TransferRequest;
import com.example.sikabethwalletapi.pojo.paystack.response.*;
import com.example.sikabethwalletapi.pojo.wallet.request.WalletValidationRequest;
import com.example.sikabethwalletapi.service.PaymentService;
import com.example.sikabethwalletapi.util.AppUtil;
import com.example.sikabethwalletapi.util.AuthDetails;
import com.example.sikabethwalletapi.util.LocalStorage;
import com.example.sikabethwalletapi.util.PayStackHttpEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
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

    @Override
    public SetUpTransactionResponse initializeTransaction(Principal principal, SetUpTransactionRequest request) {
        authDetails.validateActiveUser(principal);

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
        authDetails.validateActiveUser(principal);

        String url = "https://api.paystack.co/transaction/verify/" + reference;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                payStackHttpEntity.getEntity(),
                VerifyPaymentResponse.class
        ).getBody();
    }

    @Override
    public TransactionsResponse fetchTransactions(Principal principal) {
        authDetails.validateActiveUser(principal);

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
        authDetails.validateActiveUser(principal);

        String url = "https://api.paystack.co/bank?currency=" + currency + "&type=" + type;
        BankResponse bankResponse =  restTemplate.exchange(
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
        authDetails.validateActiveUser(principal);

        String url = "https://api.paystack.co/bank/resolve?account_number=" + accountNumber + "&bank_code=" + bankCode;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                payStackHttpEntity.getEntity(),
                VerifyAccountResponse.class
        ).getBody();
    }

    @Override
    public TransferRecipient setUpTransferRecipient(Principal principal, AccountRequest request) {
        User user = authDetails.validateActiveUser(principal);

        String url = "https://api.paystack.co/transferrecipient";
        TransferRecipient transferRecipient = restTemplate.exchange(
                url,
                HttpMethod.POST,
                payStackHttpEntity.getEntity(request),
                TransferRecipient.class
        ).getBody();
        String reference = request.getBank_code() + util.generateSerialNumber("Tsf") + "Ref";
        Objects.requireNonNull(transferRecipient).setRecipient_code(reference);

        localStorage.save(TRANSFER_RECIPIENT + user.getEmail(), reference, 3600);

        return transferRecipient;
    }

    @Override
    public TransferResponse setUpTransfer(Principal principal, TransferRequest request) {
        User user = authDetails.validateActiveUser(principal);

        String reference = request.getRecipient();
        String storedReference = localStorage.getValueByKey(TRANSFER_RECIPIENT + user.getEmail());

        if ((reference == null || storedReference == null) || !reference.equals(storedReference)) {
            throw new PaymentException("Transaction session has expired or reference does not exist. " +
                    "Kindly initiate a new transfer");
        }

        String url = "https://api.paystack.co/transfer";
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                payStackHttpEntity.getEntity(request),
                TransferResponse.class
        ).getBody();
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
}