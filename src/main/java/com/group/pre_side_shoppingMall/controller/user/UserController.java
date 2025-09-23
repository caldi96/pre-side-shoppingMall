package com.group.pre_side_shoppingMall.controller.user;

import com.group.pre_side_shoppingMall.dto.user.request.UserLoginRequest;
import com.group.pre_side_shoppingMall.dto.user.request.UserSignUpRequest;
import com.group.pre_side_shoppingMall.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request, HttpServletResponse response) {
        // 로그인 검증 후 JWT 생성
        String token = userService.login(request);

        // 쿠키 생성
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);   // JS에서 접근 불가(보안)
        cookie.setSecure(true);     // HTTPS 에서만 전송
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);  // 1시간

        response.addCookie(cookie);

        return ResponseEntity.ok("로그인 성공"); // 토큰은 쿠키로 내려감

//        return ResponseEntity.ok().body(Map.of("token", token));
    }
}
