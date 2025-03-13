package com.example.controller;

import com.example.config.RestClientConfig;
import com.example.dto.request.JoinRequest;
import com.example.dto.request.LoginRequest;
import com.example.dto.response.LoginResponse;
import com.example.jwt.JwtUtil;
import com.example.jwt.TokenInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.RemoteException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final RestClientConfig restClientConfig;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = restClientConfig.memberRestClient().get()
                .uri(uriBuilder -> uriBuilder.path("/members/auth/email")
                        .queryParam("email", loginRequest.getEmail())
                        .build()
                )
                .exchange((clientRequest, clientResponse) -> {
                    if (!clientResponse.getStatusCode().equals(HttpStatus.OK)) {
                        throw new RemoteException("로그인 실패1");
                    }

                    return clientResponse.bodyTo(LoginResponse.class);
                });

        if(loginResponse == null || !passwordEncoder.matches(loginRequest.getPassword(), loginResponse.getPassword())) {
            throw new RuntimeException("로그인 실패2");
        }


        TokenInfo tokenInfo = new TokenInfo(loginResponse);
        String accessToken = jwtUtil.createAccessToken(tokenInfo);

        return ResponseEntity
                .ok()
                .body(accessToken);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequest joinRequest) {

        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        joinRequest.encodePassword(encodedPassword);

        restClientConfig.memberRestClient().post()
                .uri(uriBuilder -> uriBuilder.path("/members/auth/join")
                        .build()
                )
                .body(joinRequest)
                .exchange((clientRequest, clientResponse) -> {
                    if (!clientResponse.getStatusCode().equals(HttpStatus.OK)) {
                        throw new RuntimeException("Status - " + clientResponse.getStatusCode() + " Text - " + clientResponse.getStatusText());
                    }

                    return clientResponse.bodyTo(Void.class);
                });

        return ResponseEntity
                .ok()
                .build();
    }

}
