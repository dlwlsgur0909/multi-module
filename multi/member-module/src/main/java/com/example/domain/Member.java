package com.example.domain;

import com.example.jwt.TokenInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_name")
    private String nickname;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_password")
    private String password;

    public void update(Member member) {
        this.nickname = member.nickname;
    }

    // 토큰 생성
    public TokenInfo toTokenInfo() {

        return TokenInfo.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
