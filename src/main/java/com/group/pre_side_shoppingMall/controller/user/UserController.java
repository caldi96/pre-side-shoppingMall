package com.group.pre_side_shoppingMall.controller.user;

import com.group.pre_side_shoppingMall.dto.user.request.UserLoginRequest;
import com.group.pre_side_shoppingMall.dto.user.request.UserSignUpRequest;
import com.group.pre_side_shoppingMall.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok().body(Map.of("token", token));
    }
}
