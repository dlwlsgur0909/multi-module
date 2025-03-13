package com.example.controller;

import com.example.config.CustomPasswordEncoder;
import com.example.domain.Member;
import com.example.dto.request.JoinRequest;
import com.example.dto.request.LoginRequest;
import com.example.dto.response.LoginResponse;
import com.example.jwt.JwtUtil;
import com.example.jwt.TokenInfo;
import com.example.repository.MemberRepository;
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

    private final CustomPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        Member findMember = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException(loginRequest.getEmail() + "에 해당하는 Member를 찾을 수 없습니다"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), findMember.getPassword())) {
            throw new RuntimeException("로그인 실패");
        }

        LoginResponse loginResponse = new LoginResponse(findMember);
        String accessToken = jwtUtil.createAccessToken(loginResponse);

        return ResponseEntity
                .ok()
                .body(accessToken);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequest joinRequest) {

        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        joinRequest.encodePassword(encodedPassword);

        if(memberRepository.existsByEmail(joinRequest.getEmail())) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        memberRepository.save(joinRequest.toEntity());

        return ResponseEntity
                .ok()
                .build();
    }

}
