package com.example.sikabethwalletapi.pojo.user.response;

import com.example.sikabethwalletapi.enums.Status;
import com.example.sikabethwalletapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 22:08
 * @project SikabethWalletAPI
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String uuid;
    private String name;
    private String email;
    private Status status;
    private String walletId;
    private Date lastLoginDate;
    private String country;
    private String state;
    private String homeAddress;
    private String phoneNumber;

    public static RegisterResponse mapFromUser(User user) {
        return RegisterResponse.builder()
                .uuid(user.getUuid())
                .name(user.getFirstName())
                .email(user.getEmail())
                .status(user.getStatus())
                .walletId(user.getWalletId())
                .lastLoginDate(user.getLastLoginDate())
                .country(user.getCountry())
                .state(user.getState())
                .homeAddress(user.getHomeAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public static List<RegisterResponse> mapFromUser(List<User> userList) {
        return userList.stream()
                .map(RegisterResponse::mapFromUser)
                .collect(Collectors.toList());
    }
}
