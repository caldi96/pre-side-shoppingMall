package com.group.pre_side_shoppingMall.service.user;

import com.group.pre_side_shoppingMall.domain.user.User;
import com.group.pre_side_shoppingMall.domain.user.UserRepository;
import com.group.pre_side_shoppingMall.dto.user.request.UserLoginRequest;
import com.group.pre_side_shoppingMall.dto.user.request.UserSignUpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    // Mockito가 @Mock, @InjectMocks 붙은 객체들을 초기화해주는 작업
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp_success() {
        // given
        UserSignUpRequest request = new UserSignUpRequest("testUser", "password123", "홍길동", 20);
        given(userRepository.findByUserName("testUser")).willReturn(Optional.empty());
        given(passwordEncoder.encode("password123")).willReturn("encodedPw");

        // when
        userService.signUp(request);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 존재하는 회원")
    void signUp_fail_duplicateUser() {
        // given
        User existingUser = new User("testUser", "encodedPw", "홍길동", 20);
        UserSignUpRequest request = new UserSignUpRequest("testUser", "password123", "홍길동", 20);

        given(userRepository.findByUserName("testUser")).willReturn(Optional.of(existingUser));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userService.signUp(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        // given
        UserLoginRequest request = new UserLoginRequest("testUser", "password123");
        User user = new User("testUser", "encodedPw", "홍길동", 20);

        given(userRepository.findByUserName("testUser")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("password123", "encodedPw")).willReturn(true);

        // when & then (예외 발생 안 하면 성공)
        userService.login(request);
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_fail_wrongPassword() {
        // given
        UserLoginRequest request = new UserLoginRequest("testUser", "wrongPw");
        User user = new User("testUser", "encodedPw", "홍길동", 20);

        given(userRepository.findByUserName("testUser")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("wrongPw", "encodedPw")).willReturn(false);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userService.login(request));
    }

}
