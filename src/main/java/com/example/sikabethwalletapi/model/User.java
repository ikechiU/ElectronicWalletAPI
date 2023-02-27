package com.example.sikabethwalletapi.model;

import com.example.sikabethwalletapi.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:25
 * @project SikabethWalletAPI
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("users")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User extends Base {

    @Indexed(unique = true)
    private String uuid;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private Status status;

    @Column(nullable = false, length = 50)
    private String encryptedPassword;

    @Column(nullable = false, length = 20)
    private String walletId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    @Column(nullable = false, length = 50)
    private String country;

    @Column(nullable = false, length = 50)
    private String state;

    @Column(nullable = false, length = 50)
    private String homeAddress;

    @Column(nullable = false, length = 14)
    private String phoneNumber;

//    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
//    @JoinTable(name = "users_roles",
//            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
//    private Collection<Role> roles;

    User () {
        super();
    }

}
