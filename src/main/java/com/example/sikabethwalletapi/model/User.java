package com.example.sikabethwalletapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:25
 * @project SikabethWalletAPI
 */

@Document("users")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

    @Indexed(unique = true)
    private String uuid;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private Status isVerified;

    @Column(nullable = false, length = 50)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

}
