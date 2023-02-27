package com.example.sikabethwalletapi.controller.trasaction;


import com.example.sikabethwalletapi.pojo.ApiResponse;
import com.example.sikabethwalletapi.pojo.wallet.request.InitiateTransferFromSikabethToWalletRequest;
import com.example.sikabethwalletapi.pojo.wallet.request.PinResetRequest;
import com.example.sikabethwalletapi.pojo.wallet.request.WalletValidationRequest;
import com.example.sikabethwalletapi.service.TransactionService;
import com.example.sikabethwalletapi.service.WalletService;
import com.example.sikabethwalletapi.util.ResponseProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 20:29
 * @project SikabethWalletAPI
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wallet-transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final ResponseProvider responseProvider;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getTransaction(Principal principal, @PathVariable String id) {
        return responseProvider.success(transactionService.fetchTransaction(principal, id));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> getTransactions(Principal principal,
                                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return responseProvider.success(transactionService.fetchTransactions(principal, page, limit));
    }

    @GetMapping("/type")
    public ResponseEntity<ApiResponse<Object>> getTransactions(Principal principal,
                                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "limit", defaultValue = "5") int limit,
                                                                     @Parameter(description = "CREDIT OR DEBIT")
                                                                     @RequestParam String type) {
        return responseProvider.success(transactionService.fetchTransactions(principal, page, limit, type));
    }


}
