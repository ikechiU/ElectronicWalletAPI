package com.example.sikabethwalletapi.service;

import com.example.sikabethwalletapi.pojo.user.request.*;
import com.example.sikabethwalletapi.pojo.user.response.LoginResponse;
import com.example.sikabethwalletapi.pojo.user.response.RegisterResponse;
import com.example.sikabethwalletapi.pojo.user.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:12
 * @project SikabethWalletAPI
 */

public interface UserService extends UserDetailsService {
    RegisterResponse createUser(RegisterRequest request);
    UserResponse getUser(Principal principal);
    List<UserResponse> getUsers(Principal principal, int page, int limit);
    LoginResponse login(LoginRequest request);
    String activateUser(ActivationRequest request);
    String resendActivationToken(String email);
    String delete(Principal principal);
    String forgotPassword(String email);
    String resetPassword(PasswordResetRequest request);
    RegisterResponse updateUser(Principal principal, UpdateRequest request);
    String logout(Principal principal);
    String updatePassword(Principal principal, String password);
}
