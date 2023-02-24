package com.example.sikabethwalletapi.service.impl;

import com.example.sikabethwalletapi.enums.VerificationStatus;
import com.example.sikabethwalletapi.exception.WalletException;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.model.Wallet;
import com.example.sikabethwalletapi.pojo.paystack.response.CustomerValidationResponse;
import com.example.sikabethwalletapi.pojo.wallet.request.WalletValidationRequest;
import com.example.sikabethwalletapi.pojo.wallet.response.WalletResponse;
import com.example.sikabethwalletapi.repository.WalletRepository;
import com.example.sikabethwalletapi.service.PaymentService;
import com.example.sikabethwalletapi.service.WalletService;
import com.example.sikabethwalletapi.util.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 18:39
 * @project SikabethWalletAPI
 */

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final AuthDetails authDetails;
    private final WalletRepository walletRepository;
    private final PasswordEncoder encoder;
    private final PaymentService paymentService;


    @Override
    public WalletResponse getWallet(Principal principal) {
        return WalletResponse.mapFromWallet(getUserWallet(principal));
    }

    @Override
    public List<WalletResponse> getWallets(Principal principal, int page, int limit) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, limit);
        Page<Wallet> pagedWallets = walletRepository.findAll(pageable);
        List<Wallet> wallets = pagedWallets.getContent();
        return WalletResponse.mapFromWallet(wallets);
    }

    @Override
    public WalletResponse transferMoney(Principal principal, String recipientWalletId, BigDecimal amount, String pin) {
        checksBeforeTransaction(principal);


        return null;
    }

    @Override
    public WalletResponse payService(Principal principal, String serviceName, BigDecimal amount, String pin) {
        checksBeforeTransaction(principal);


        return null;
    }

    @Override
    public String validateWallet(Principal principal, WalletValidationRequest request) {
        Wallet wallet = getUserWallet(principal);

        //N/B: THE VALIDATION OUGHT TO BE WITH CURRENT USER'S INFORMATION OR RATHER COLLECT ONLY BVN, NIN
        //AND BANK THEN DO A VALIDATION COMPARE IF NAME, PHONE AND OTHER DATA THEY PRESENTED MATCHES
        //I.E WHAT HAS BEEN STORED IN DATABASE. ANOTHER EXTRA LAYER WOULD BE TO COLLECT FINGERPRINTS
        //AND PICTURE OF USE AND COMPARE WITH BVN AND NIN VALIDATION
        CustomerValidationResponse response = paymentService.validateCustomer(request);
        if (response.isStatus() && response.getMessage().equals("Customer Identification in progress")) {
            wallet.setVerificationStatus(VerificationStatus.PENDING);
            walletRepository.save(wallet);
        }
        return response.getMessage();
    }

    private Wallet getUserWallet(Principal principal) {
        User user = authDetails.validateActiveUser(principal);
        return walletRepository.findByWalletId(user.getWalletId())
                .orElseThrow(() -> new WalletException("Wallet does not exist."));
    }

    private void getWalletStatus(Wallet wallet) {
        if (wallet.getVerificationStatus().equals(VerificationStatus.PENDING))
            throw new WalletException("Kindly wait as your verification is in progress");

        if (!wallet.isVerified())
            throw new WalletException("Kindly validate your wallet");
    }

    private void checkDefaultPin(Wallet wallet) {
        boolean isDefault = encoder.matches(wallet.getPin(), "0000");
        if (isDefault)
            throw new WalletException("Please, change your pin");
    }

    private void checksBeforeTransaction(Principal principal) {
        Wallet wallet = getUserWallet(principal);
        getWalletStatus(wallet);
        checkDefaultPin(wallet);
    }
}
