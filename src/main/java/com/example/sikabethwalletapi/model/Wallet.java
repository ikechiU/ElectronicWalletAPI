package com.example.sikabethwalletapi.model;

import com.example.sikabethwalletapi.enums.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("wallets")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Wallet extends Base {

    @Indexed(unique = true)
    private String uuid;

    @Indexed(unique = true)
    private String walletId;

    @Column(nullable = false, length = 50)
    private BigDecimal balance;

    @Min(value = 4)
    @Max(value = 4)
    @Column(nullable = false, length = 4)
    private String pin;

    private String bvn;
    private boolean isVerified;
    private VerificationStatus verificationStatus;
    private String customer_code;
    private boolean isBlacklisted;

    public Wallet() {
        super();
    }

}
