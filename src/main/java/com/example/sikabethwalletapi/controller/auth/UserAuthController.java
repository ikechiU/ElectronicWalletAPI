package com.example.sikabethwalletapi.controller.auth;

import com.example.sikabethwalletapi.pojo.ApiResponse;
import com.example.sikabethwalletapi.pojo.request.ActivationRequest;
import com.example.sikabethwalletapi.pojo.request.LoginRequest;
import com.example.sikabethwalletapi.pojo.request.PasswordResetRequest;
import com.example.sikabethwalletapi.pojo.request.RegisterRequest;
import com.example.sikabethwalletapi.service.UserService;
import com.example.sikabethwalletapi.util.ResponseProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ikechi Ucheagwu
 * @created 23/02/2023 - 21:48
 * @project SikabethWalletAPI
 */


@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserAuthController {

    private final UserService userService;
    private final ResponseProvider responseProvider;

    @SecurityRequirements
    @PostMapping("/user/register")
    public ResponseEntity<ApiResponse<Object>> createUser(@RequestBody RegisterRequest request){
        return responseProvider.success("Request Successful, kindly check your email to activate account",
                userService.createUser(request));
    }

    @SecurityRequirements
    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse<Object>> authenticateUser(@RequestBody LoginRequest request){
        return responseProvider.success(userService.login(request));
    }

    @SecurityRequirements
    @GetMapping("/echo")
    public ResponseEntity<ApiResponse<Object>> echo(){
        return responseProvider.success("Live and running!!!");
    }

    @SecurityRequirements
    @PostMapping("/user/activate")
    public ResponseEntity<ApiResponse<Object>> activateUser(@RequestBody ActivationRequest request){
        return responseProvider.success(userService.activateUser(request));
    }

    @SecurityRequirements
    @PostMapping("/user/resend-token")
    public ResponseEntity<ApiResponse<Object>> resendUserToken(@RequestParam("email") String email){
        return responseProvider.success(userService.resendActivationToken(email));
    }

    @SecurityRequirements
    @PostMapping("/user/forgot-password")
    public ResponseEntity<ApiResponse<Object>> forgotPassword(@RequestParam("email") String email){
        return responseProvider.success(userService.forgotPassword(email));
    }

    @SecurityRequirements
    @PostMapping("/user/reset-password")
    public ResponseEntity<ApiResponse<Object>> resetPassword(@RequestBody PasswordResetRequest request) {
        return responseProvider.success(userService.resetPassword(request));
    }

}
