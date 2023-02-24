package com.example.sikabethwalletapi.exception;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 21:05
 * @project SikabethWalletAPI
 */

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
