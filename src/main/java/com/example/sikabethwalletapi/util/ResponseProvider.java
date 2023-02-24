package com.example.sikabethwalletapi.util;

import com.example.sikabethwalletapi.pojo.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Ikechi Ucheagwu
 * @created 08/12/2022 - 00:09
 * @project Decapay
 */

@Service
@AllArgsConstructor
public class ResponseProvider {
    public ResponseEntity<ApiResponse<Object>> success(Object payload) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Request Successful", true, payload));
    }

    public ResponseEntity<ApiResponse<Object>> success(String message, Object payload) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(message, true, payload));
    }
}

