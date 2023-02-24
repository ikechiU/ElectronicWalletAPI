package com.example.sikabethwalletapi.exception;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 21:05
 * @project SikabethWalletAPI
 */

public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }

}
