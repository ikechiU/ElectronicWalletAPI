package com.example.sikabethwalletapi.controller.user;


import com.example.sikabethwalletapi.pojo.ApiResponse;
import com.example.sikabethwalletapi.pojo.user.request.UpdateRequest;
import com.example.sikabethwalletapi.service.UserService;
import com.example.sikabethwalletapi.util.ResponseProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @GetMapping("/id")
    public ResponseEntity<ApiResponse<Object>> getUser(Principal principal) {
        return responseProvider.success(userService.getUser(principal));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> getUsers(Principal principal,
                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return responseProvider.success(userService.getUsers(principal, page, limit));
    }

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
