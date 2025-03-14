package com.example.dto.response;

import com.example.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private Long id;
    private String nickname;
    private String email;
    private String accessToken;

    public LoginResponse(Member member, String accessToken) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.accessToken = accessToken;
    }

}
