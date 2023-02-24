package com.example.sikabethwalletapi.pojo.paystack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 13:51
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private long id;
    private String status;
    private String reference;
    private long amount;
    private String message;
    private String gateway_response;
    private String paid_at;
    private String created_at;
    private String channel;
    private String currency;
    private String ip_address;
    private String metadata;
    private String fees;
    private String fees_split;
    private Authorization authorization;
    private Customer customer;

    //    "plan": null,
    //    "split": {},
    //    "order_id": null,
    //    "paidAt": "2022-08-09T14:21:32.000Z",
    //    "createdAt": "2022-08-09T14:20:57.000Z",
    //    "requested_amount": 20000,
    //    "pos_transaction_data": null,
    //    "source": null,
    //    "fees_breakdown": null,
    //    "transaction_date": "2022-08-09T14:20:57.000Z",
    //    "plan_object": {},
    //    "subaccount": {}
    //  }
    //}

}
