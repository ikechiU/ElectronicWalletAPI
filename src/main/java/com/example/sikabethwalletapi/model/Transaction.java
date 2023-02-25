package com.example.sikabethwalletapi.model;

import com.example.sikabethwalletapi.enums.TransactionSource;
import com.example.sikabethwalletapi.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("transactions")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Transaction extends Base {

    @Indexed(unique = true)
    private String uuid;

    private String userUuid;

    @Column(nullable = false, length = 50)
    private String reason;

    @Column(nullable = false, length = 50)
    private String from;

    @Column(nullable = false, length = 50)
    private String to;

    private BigDecimal amount;

    private BigDecimal balance;

    @Column(nullable = false, length = 50)
    private String transactionReference;

    private TransactionType transactionType;

    private TransactionSource transactionSource;

    public Transaction() {
        super();
    }

}
