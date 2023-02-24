package com.example.sikabethwalletapi.controller.wallet;


import com.example.sikabethwalletapi.pojo.ApiResponse;
import com.example.sikabethwalletapi.pojo.wallet.request.WalletValidationRequest;
import com.example.sikabethwalletapi.service.WalletService;
import com.example.sikabethwalletapi.util.ResponseProvider;
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
@RequestMapping("api/v1/wallet")
public class WalletController {

    private final WalletService walletService;
    private final ResponseProvider responseProvider;

    @GetMapping("/id")
    public ResponseEntity<ApiResponse<Object>> getWallet(Principal principal) {
        return responseProvider.success(walletService.getWallet(principal));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> getWallets(Principal principal,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return responseProvider.success(walletService.getWallets(principal, page, limit));
    }

    @PostMapping("/transfer-money")
    public ResponseEntity<ApiResponse<Object>> transferMoney(Principal principal,
                                                             @RequestParam String recipientWalletId,
                                                             @RequestParam BigDecimal amount,
                                                             @RequestParam String pin) {
        return responseProvider.success(walletService.transferMoney(principal, recipientWalletId, amount, pin));
    }

    @PostMapping("/pay-service")
    public ResponseEntity<ApiResponse<Object>> payService(Principal principal,
                                                             @RequestParam String serviceName,
                                                             @RequestParam BigDecimal amount,
                                                             @RequestParam String pin) {
        return responseProvider.success(walletService.payService(principal, serviceName, amount, pin));
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<Object>> validateWallet(Principal principal, @RequestBody WalletValidationRequest request) {
        return responseProvider.success(walletService.validateWallet(principal, request));
    }


}
