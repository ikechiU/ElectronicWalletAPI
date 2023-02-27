package com.example.sikabethwalletapi.pojo.user.response;

import com.example.sikabethwalletapi.enums.Status;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.model.Wallet;
import com.example.sikabethwalletapi.pojo.wallet.response.WalletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 26/02/2023 - 11:13
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserResponse {
    private RegisterResponse user;
    private WalletResponse wallet;


    public static UserResponse mapFromData(User userData, Wallet userWallet) {
        RegisterResponse user = RegisterResponse.mapFromUser(userData);
        WalletResponse wallet = WalletResponse.mapFromWallet(userWallet);

        return UserResponse.builder()
                .user(user)
                .wallet(wallet)
                .build();
    }

    public static List<UserResponse> mapFromData(List<User> userList, List<Wallet> walletList) {
        List<RegisterResponse> users = RegisterResponse.mapFromUser(userList);
        List<WalletResponse> wallets = WalletResponse.mapFromWallet(walletList);

        List<UserResponse> userResponses = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            RegisterResponse user = users.get(i);

            WalletResponse wallet;
            try {
                wallet = wallets.get(i);
            } catch (Exception e) {
                wallet = null;
            }

            var userResponse = UserResponse.builder()
                    .user(user)
                    .wallet(wallet)
                    .build();

            userResponses.add(userResponse);
        }

        return userResponses;
    }
}
