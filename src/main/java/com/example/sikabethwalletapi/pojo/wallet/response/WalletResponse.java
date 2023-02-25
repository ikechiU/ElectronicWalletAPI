package com.example.sikabethwalletapi.pojo.wallet.response;

import com.example.sikabethwalletapi.enums.VerificationStatus;
import com.example.sikabethwalletapi.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 19:20
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class WalletResponse {
    private String uuid;
    private String userUuid;
    private String walletId;
    private BigDecimal balance;
    private String bvn;
    private boolean isVerified;
    private String customer_code;
    private boolean isBlacklisted;
    private String transactionReference;
    private VerificationStatus verificationStatus;

    public static WalletResponse mapFromWallet(Wallet wallet) {
        return WalletResponse.builder()
                .uuid(wallet.getUuid())
                .userUuid(wallet.getUserUuid())
                .walletId(wallet.getWalletId())
                .balance(wallet.getBalance())
                .bvn(wallet.getBvn())
                .isVerified(wallet.isVerified())
                .customer_code(wallet.getCustomer_code())
                .isBlacklisted(wallet.isBlacklisted())
                .verificationStatus(wallet.getVerificationStatus())
                .build();
    }

    public static List<WalletResponse> mapFromWallet(List<Wallet> wallets) {
        return wallets.stream()
                .map(WalletResponse::mapFromWallet)
                .collect(Collectors.toList());
    }
}
