package com.example.sikabethwalletapi.controller.paystack;

import com.example.sikabethwalletapi.pojo.ApiResponse;
import com.example.sikabethwalletapi.pojo.paystack.request.AccountRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.SetUpTransactionRequest;
import com.example.sikabethwalletapi.pojo.paystack.request.TransferRequest;
import com.example.sikabethwalletapi.service.PaymentService;
import com.example.sikabethwalletapi.util.ResponseProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 04:55
 * @project SikabethWalletAPI
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vi/transaction")
public class PayStackController {

    private final PaymentService paymentService;

    private final ResponseProvider responseProvider;

    @PostMapping("/initialize")
    public ResponseEntity<ApiResponse<Object>> setUpTransaction(Principal principal, SetUpTransactionRequest request) {
        return responseProvider.success(paymentService.initializeTransaction(principal, request));
    }

    @GetMapping("/verify-reference")
    public ResponseEntity<ApiResponse<Object>> verifyPayment(Principal principal, @RequestParam String reference) {
        return responseProvider.success(paymentService.verifyTransaction(principal, reference));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> fetchTransactions(Principal principal) {
        return responseProvider.success(paymentService.fetchTransactions(principal));
    }

    @GetMapping("/id")
    public ResponseEntity<ApiResponse<Object>> fetchTransaction(Principal principal, @RequestParam long id) {
        return responseProvider.success(paymentService.fetchTransaction(principal, id));
    }

    @GetMapping("/banks")
    public ResponseEntity<ApiResponse<Object>> fetchBanks(Principal principal,
                                                          @RequestParam(value = "currency", defaultValue = "NGN") String currency,
                                                          @RequestParam(value = "type", defaultValue = "nuban") String type ) {
        return responseProvider.success(paymentService.fetchBanks(principal, currency, type));
    }

    @GetMapping("/verify-account-number")
    public ResponseEntity<ApiResponse<Object>> verifyAccount(Principal principal,
                                                             @RequestParam String accountNumber,
                                                             @RequestParam String bankCode) {
        return responseProvider.success(paymentService.verifyAccount(principal, accountNumber, bankCode));
    }

    @PostMapping("/transfer-recipient")
    public ResponseEntity<ApiResponse<Object>> generateTransferRecipient(Principal principal, AccountRequest request) {
        return responseProvider.success(paymentService.setUpTransferRecipient(principal, request));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<Object>> initiateTransfer(Principal principal, TransferRequest request) {
        return responseProvider.success(paymentService.setUpTransfer(principal, request));
    }
}
