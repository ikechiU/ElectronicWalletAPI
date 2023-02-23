package com.example.sikabethwalletapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Document("wallets")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Wallet extends Base {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    @Indexed(unique = true)
    private String walletId;

    @Column(nullable = false, length = 50)
    private BigDecimal balance;

    public Wallet() {super();}

}
