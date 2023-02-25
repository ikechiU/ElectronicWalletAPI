package com.example.sikabethwalletapi.model;

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
@Document("payments")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Payment extends Base {

    @Indexed(unique = true)
    private String uuid;

    @Column(nullable = false, length = 50)
    private String walletId;

    @Column(nullable = false, length = 50)
    private String from;

    @Column(nullable = false, length = 50)
    private String to;

    private boolean confirmed;

    @Column(nullable = false, length = 50)
    private BigDecimal amount;

    @Column(nullable = false)
    private String transferCode;

    @Column(nullable = false)
    private String reference;

    public Payment() {
        super();
    }

}
