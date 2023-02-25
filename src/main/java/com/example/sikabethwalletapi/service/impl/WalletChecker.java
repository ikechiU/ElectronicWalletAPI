package com.example.sikabethwalletapi.service.impl;

import com.example.sikabethwalletapi.enums.VerificationStatus;
import com.example.sikabethwalletapi.exception.WalletException;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.model.Wallet;
import com.example.sikabethwalletapi.repository.WalletRepository;
import com.example.sikabethwalletapi.util.AuthDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * @author Ikechi Ucheagwu
 * @created 25/02/2023 - 18:38
 * @project SikabethWalletAPI
 */


@Component
public class WalletChecker {

    public void checksBeforeTransaction(Wallet wallet, PasswordEncoder encoder) {
       getWalletStatus(wallet);
       checkDefaultPin(encoder, wallet);
    }

    private void getWalletStatus(Wallet wallet) {
        if (wallet.getVerificationStatus().equals(VerificationStatus.PENDING))
            throw new WalletException("Kindly wait as your verification is in progress");

        if (!wallet.isVerified())
            throw new WalletException("Kindly validate your wallet");
    }

    private void checkDefaultPin(PasswordEncoder encoder, Wallet wallet) {
        boolean isDefault = encoder.matches( "0000", wallet.getPin());
        if (isDefault)
            throw new WalletException("Please, change your pin");
    }
}
