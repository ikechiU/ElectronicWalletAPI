package com.example.sikabethwalletapi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 04:27
 * @project SikabethWalletAPI
 */


@Component
public class PayStackHttpEntity<T> {
    @Value("${secret.key}")
    private String SECRET_KEY;

    public HttpEntity<T> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + SECRET_KEY);
        return new HttpEntity<>(headers);
    }

    public HttpEntity<T> getEntity(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + SECRET_KEY);
        return new HttpEntity<>(body, headers);
    }
}
