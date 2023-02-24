package com.example.sikabethwalletapi.exception;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 21:05
 * @project SikabethWalletAPI
 */

public class WalletException extends RuntimeException {

    public WalletException(String message) {
        super(message);
    }

}
