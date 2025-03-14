package com.example.service;

import com.example.config.CustomPasswordEncoder;
import com.example.domain.Member;
import com.example.dto.request.JoinRequest;
import com.example.dto.request.LoginRequest;
import com.example.dto.response.LoginResponse;
import com.example.jwt.JwtUtil;
import com.example.jwt.TokenInfo;
import com.example.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final CustomPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse login(final LoginRequest request) {

        Member findMember = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(request.getEmail() + "에 해당하는 Member를 찾을 수 없습니다"));

        if(!passwordEncoder.matches(request.getPassword(), findMember.getPassword())) {
            throw new RuntimeException("로그인 실패");
        }

        String accessToken = jwtUtil.createAccessToken(findMember.toTokenInfo());

        return new LoginResponse(findMember, accessToken);
    }

    @Transactional
    public void join(final JoinRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.encodePassword(encodedPassword);

        if(memberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        memberRepository.save(request.toEntity());
    }


}
