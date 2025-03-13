package com.example.jwt;

import com.example.dto.response.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenInfo {

    private Long id;
    private String nickname;
    private String email;

    public TokenInfo(LoginResponse loginResponse) {
        this.id = loginResponse.getId();
        this.nickname = loginResponse.getNickname();
        this.email = loginResponse.getEmail();
    }

}
