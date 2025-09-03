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

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest request) {
        userService.login(request);
        return "로그인 성공";
    }
}
