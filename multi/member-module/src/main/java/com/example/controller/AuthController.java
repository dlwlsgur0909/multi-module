package com.example.controller;

import com.example.config.CustomPasswordEncoder;
import com.example.domain.Member;
import com.example.dto.request.JoinRequest;
import com.example.dto.request.LoginRequest;
import com.example.dto.response.LoginResponse;
import com.example.jwt.JwtUtil;
import com.example.jwt.TokenInfo;
import com.example.repository.MemberRepository;
import com.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        return ResponseEntity
                .ok()
                .body(authService.login(request));
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequest request) {

        authService.join(request);

        return ResponseEntity
                .ok()
                .build();
    }

}
