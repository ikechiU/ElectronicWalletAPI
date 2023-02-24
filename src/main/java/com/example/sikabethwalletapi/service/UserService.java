package com.example.sikabethwalletapi.service;

import com.example.sikabethwalletapi.pojo.request.ActivationRequest;
import com.example.sikabethwalletapi.pojo.request.LoginRequest;
import com.example.sikabethwalletapi.pojo.request.PasswordResetRequest;
import com.example.sikabethwalletapi.pojo.request.RegisterRequest;
import com.example.sikabethwalletapi.pojo.response.LoginResponse;
import com.example.sikabethwalletapi.pojo.response.RegisterResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:12
 * @project SikabethWalletAPI
 */

public interface UserService extends UserDetailsService {
    RegisterResponse createUser(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    String activateUser(ActivationRequest request);
    String resendActivationToken(String email);
    String forgotPassword(String email);
    String resetPassword(PasswordResetRequest request);
    String logout(Principal principal);
}
