package com.example.sikabethwalletapi.controller.user;


import com.example.sikabethwalletapi.pojo.ApiResponse;
import com.example.sikabethwalletapi.service.UserService;
import com.example.sikabethwalletapi.util.ResponseProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private  final UserService userService;
    private  final ResponseProvider responseProvider;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(Principal principal) {
        return responseProvider.success(userService.logout(principal));
    }
}
