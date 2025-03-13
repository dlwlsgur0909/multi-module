package com.example.dto.response;

import com.example.domain.Member;
import com.example.jwt.TokenInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse implements TokenInterface {

    private Long id;
    private String nickname;
    private String email;

    public LoginResponse(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }

}
