package com.example.sikabethwalletapi.controller.user;


import com.example.sikabethwalletapi.pojo.ApiResponse;
import com.example.sikabethwalletapi.pojo.request.UpdateRequest;
import com.example.sikabethwalletapi.service.UserService;
import com.example.sikabethwalletapi.util.ResponseProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private  final UserService userService;
    private  final ResponseProvider responseProvider;

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Object>> update(Principal principal, UpdateRequest request) {
        return responseProvider.success(userService.updateUser(principal, request));
    }

    @PutMapping("/update-password")
    public ResponseEntity<ApiResponse<Object>> updatePassword(Principal principal, String password) {
        return responseProvider.success(userService.updatePassword(principal, password));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(Principal principal) {
        return responseProvider.success(userService.logout(principal));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Object>> delete(Principal principal) {
        return responseProvider.success(userService.delete(principal));
    }
}
