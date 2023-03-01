package com.example.sikabethwalletapi.util;

import com.example.sikabethwalletapi.exception.UserNotFoundException;
import com.example.sikabethwalletapi.exception.ValidationException;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthDetails {
    @Value(value = "${auth.user}")
    private String AUTH_USER;
    @Value(value = "${active.auth.user}")
    private String ACTIVE_AUTH_USER;
    private final UserRepository userRepository;
    private final LocalStorage localStorage;

    private User getAuthorizedUser(Principal principal) {
        if (principal != null) {
            final String email = principal.getName();
            return userRepository.findByEmail(email)
                    .orElseThrow(()-> new UserNotFoundException("Kindly, login to access your dashboard."));
        } else{
            throw new UserNotFoundException("Kindly, login to access your dashboard.");
        }
    }

    public User validateActiveUser(Principal principal) {
        User user = getAuthorizedUser(principal);

        String activeTokenKey = ACTIVE_AUTH_USER + user.getEmail();
        String tokenKey = AUTH_USER + user.getEmail();

        String activeToken = localStorage.getValueByKey(activeTokenKey);
        String token = localStorage.getValueByKey(tokenKey);

        log.info("ActiveAuthUser: " + activeToken);
        log.info("AuthUser: " + token);

        if (activeToken == null || !activeToken.equals(token))
            throw new ValidationException("Token expired. Kindly, login again.");

        return user;
    }
}
