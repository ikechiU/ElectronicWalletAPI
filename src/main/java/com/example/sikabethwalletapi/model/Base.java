package com.example.sikabethwalletapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:13
 * @project SikabethWalletAPI
 */

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
public abstract class Base implements Serializable {

    private static final long serialVersionUID  = -3290965513559831382L;

    @Id
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    Base(){
        this.createdDate= new Date();
        this.updatedDate = new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdDate = new Date();
    }

    @PreUpdate
    private void setUpdatedAt() {
        updatedDate = new Date();
    }

}
