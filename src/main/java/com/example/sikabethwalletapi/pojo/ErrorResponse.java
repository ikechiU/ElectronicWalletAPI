package com.example.sikabethwalletapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 17:51
 * @project demo_security
 */

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private Collection message;
    private HttpStatus error;
    private int code;
    private Timestamp timestamp;
}
