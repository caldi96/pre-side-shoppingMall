package com.group.pre_side_shoppingMall.config;

import com.group.pre_side_shoppingMall.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // jwt 없이 임시 회원가입 허용 코드(나중에 jwt 구현하고 지워야함)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.csrf(csrf -> csrf.disable()) 스프링부트 2.x CSRF 비활성화
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화 (스프링부트 3.x부터)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/signUp", "/api/auth/login").permitAll() // 회원가입, 로그인 허용
                        .requestMatchers("/product/**").permitAll() // 나중에 지워야함
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
