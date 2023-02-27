package com.example.sikabethwalletapi.service.impl;

import com.example.sikabethwalletapi.enums.Status;
import com.example.sikabethwalletapi.enums.VerificationStatus;
import com.example.sikabethwalletapi.exception.AuthenticationException;
import com.example.sikabethwalletapi.exception.UserNotFoundException;
import com.example.sikabethwalletapi.exception.ValidationException;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.model.Wallet;
import com.example.sikabethwalletapi.pojo.Mapper;
import com.example.sikabethwalletapi.pojo.paystack.response.CreateCustomerResponse;
import com.example.sikabethwalletapi.pojo.user.request.*;
import com.example.sikabethwalletapi.pojo.user.response.LoginResponse;
import com.example.sikabethwalletapi.pojo.user.response.RegisterResponse;
import com.example.sikabethwalletapi.pojo.user.response.UserResponse;
import com.example.sikabethwalletapi.repository.UserRepository;
import com.example.sikabethwalletapi.repository.WalletRepository;
import com.example.sikabethwalletapi.security.JwtUtils;
import com.example.sikabethwalletapi.security.UserPrincipal;
import com.example.sikabethwalletapi.service.PaymentService;
import com.example.sikabethwalletapi.service.UserService;
import com.example.sikabethwalletapi.util.AmazonSES;
import com.example.sikabethwalletapi.util.AppUtil;
import com.example.sikabethwalletapi.util.AuthDetails;
import com.example.sikabethwalletapi.util.LocalStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 20:12
 * @project SikabethWalletAPI
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value(value = "${user.email.activate}")
    private String USER_EMAIL_ACTIVATE;

    @Value(value = "${user.password.forgot}")
    private String USER_PASSWORD_FORGOT;

    @Value(value = "${auth.user}")
    private String AUTH_USER;

    @Value(value = "${active.auth.user}")
    private String ACTIVE_AUTH_USER;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final WalletRepository walletRepository;
    private final JwtUtils jwtUtils;
    private final LocalStorage localStorage;
    private final AuthDetails authDetails;
    private final AppUtil util;

    private final AuthenticationManager authenticationManager;
    private final AmazonSES amazonSES;

    private final PaymentService paymentService;

    @Override
    public RegisterResponse createUser(RegisterRequest request) {
        if (!util.validEmail(request.getEmail()))
            throw new ValidationException("Invalid email address");

        boolean userExist = userRepository.existsByEmail(request.getEmail());
        if (userExist)
            throw new ValidationException("User already exist");

        User newUser = Mapper.mapUser(request);

        String token = util.generateSerialNumber("A");
        localStorage.save(USER_EMAIL_ACTIVATE + request.getEmail(), token, 432000);

        amazonSES.verifyEmail(token, request.getEmail());

        User createdUser = userRepository.save(newUser);
        return RegisterResponse.mapFromUser(createdUser);
    }

    @Override
    public UserResponse getUser(Principal principal) {
        User user = authDetails.validateActiveUser(principal);
        Wallet wallet = getWallet(user.getWalletId());

        return UserResponse.mapFromData(user, wallet);
    }

    @Override
    public List<UserResponse> getUsers(Principal principal, int page, int limit) {
        authDetails.validateActiveUser(principal);

        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, limit);

        Page<User> pagedUser = userRepository.findAll(pageable);
        List<User> users = pagedUser.getContent();

        Page<Wallet> pagedWallet = walletRepository.findAll(pageable);
        List<Wallet> wallets = pagedWallet.getContent();

        return UserResponse.mapFromData(users, wallets);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = getUser(request.getEmail());
        if (user.getStatus().name().equals(Status.INACTIVE.name()))
            throw new ValidationException("Kindly activate your account");

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (!authentication.isAuthenticated()) {
            throw new AuthenticationException("Invalid Username or Password");
        }

        user.setLastLoginDate(new Date());
        userRepository.save(user);

        String token = jwtUtils.generateToken(user.getEmail());

        String activeTokenKey = ACTIVE_AUTH_USER + request.getEmail();
        localStorage.saveToken(activeTokenKey, token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return LoginResponse.mapFromData(token, user.getEmail());
    }

    @Override
    public String activateUser(ActivationRequest request) {
        validateEmail(request.getEmail());
        User user = getUser(request.getEmail());

        if (user.getStatus() == Status.ACTIVE)
            return "Account already activated";

        String systemToken = localStorage.getValueByKey(USER_EMAIL_ACTIVATE + request.getEmail());
        if (systemToken == null)
            throw new ValidationException("Token expired");

        if (!systemToken.equalsIgnoreCase(request.getToken()))
            throw new ValidationException("Invalid token");

        CreateCustomerResponse response = paymentService.createCustomer(Mapper.mapFromUser(user));
        if (!response.isStatus()) throw new RuntimeException("Something went wrong try again");

        String customer_code = response.getData().getCustomer_code();
        log.info(customer_code);
        Wallet wallet = generateWallet();
        wallet.setCustomer_code(customer_code);
        wallet.setEmail(user.getEmail());
        wallet.setUserUuid(user.getUuid());
        wallet.setAccountActive(true);
        walletRepository.save(wallet);

        user.setWalletId(wallet.getWalletId());
        user.setStatus(Status.ACTIVE);
        user.setUpdatedDate(new Date());
        userRepository.save(user);

        localStorage.clear(USER_EMAIL_ACTIVATE + request.getEmail());

        return "Account successfully activated";
    }

    @Override
    public String resendActivationToken(String email) {
        validateEmail(email);
        User user = getUser(email);
        if (user.getStatus() == Status.ACTIVE)
            return "Account already activated";

        String token = util.generateSerialNumber("A");
        localStorage.save(USER_EMAIL_ACTIVATE + email, token, 432000);

        amazonSES.verifyEmail(token, email);
        return "Token has been sent successfully";
    }

    @Override
    public String delete(Principal principal) {
        User user = authDetails.validateActiveUser(principal);
        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Override
    public String forgotPassword(String email) {
        validateEmail(email);
        User user = getUser(email);
        String token = util.generateSerialNumber("F");
        localStorage.save(USER_PASSWORD_FORGOT + email, token, 432000);

        amazonSES.sendPasswordResetRequest(user.getFirstName(), token, email);
        return "Token has been sent successfully";
    }

    @Override
    public String resetPassword(PasswordResetRequest request) {
        validateEmail(request.getEmail());
        User user = getUser(request.getEmail());

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ValidationException("Passwords do not match");
        }

        user.setEncryptedPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        userRepository.save(user);

        return "Password reset successful";
    }

    @Override
    public RegisterResponse updateUser(Principal principal, UpdateRequest request) {
        User user = authDetails.validateActiveUser(principal);
        User updatedUser = userRepository.save(Mapper.mapUser(user, request));
        return RegisterResponse.mapFromUser(updatedUser);
    }

    @Override
    public String logout(Principal principal) {
        User user = authDetails.validateActiveUser(principal);

        String activeTokenKey = ACTIVE_AUTH_USER + user.getEmail();
        String tokenKey = AUTH_USER + user.getEmail();

        localStorage.clear(activeTokenKey);
        localStorage.clear(tokenKey);

        return "Logout successful";
    }

    @Override
    public String updatePassword(Principal principal, String password) {
        User user = authDetails.validateActiveUser(principal);
        user.setEncryptedPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "Password updated successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(getUser(username));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User does not exist."));
    }
    private void validateEmail(String email) {
        if (!util.validEmail(email))
            throw new ValidationException("Invalid email address");
    }

    private Wallet getWallet(String walletId) {
        return walletRepository.findByWalletId(walletId)
                .orElse(null);
    }

    private Wallet generateWallet() {
        return Wallet.builder()
                .uuid(UUID.randomUUID().toString())
                .walletId(UUID.randomUUID().toString())
                .pin(passwordEncoder.encode("0000"))
                .balance(BigDecimal.ZERO)
                .isVerified(false)
                .bvn(null)
                .verificationStatus(VerificationStatus.UNCONFIRMED)
                .isBlacklisted(false)
                .build();
    }
}
