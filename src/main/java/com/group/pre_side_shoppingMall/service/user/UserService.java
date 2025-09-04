package com.group.pre_side_shoppingMall.service.user;

import com.group.pre_side_shoppingMall.config.jwt.JwtUtil;
import com.group.pre_side_shoppingMall.domain.user.User;
import com.group.pre_side_shoppingMall.domain.user.UserRepository;
import com.group.pre_side_shoppingMall.dto.user.request.UserLoginRequest;
import com.group.pre_side_shoppingMall.dto.user.request.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // BCrypt
    private final JwtUtil jwtUtil;

    // 회원가입
    public void signUp(UserSignUpRequest request) {
        // 아이디 중복 체크
        userRepository.findByUserName(request.getUserName())
                .ifPresent(user -> {
                    throw new IllegalArgumentException(String.format("%s는 이미 존재하는 회원입니다.", user.getUserName())); // 에러가 나면 코드 코치기
                });

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUserName(),
                encodedPassword,
                request.getName(),
                request.getAge()
        );

        userRepository.save(user);
    }

    // 로그인 시 JWT 토큰 반환
    public String login(UserLoginRequest request) {
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        // 비밀번호 체크
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        // 로그인 성공 -> 토큰 발급
        return jwtUtil.createToken(user.getUserName());
    }

}
