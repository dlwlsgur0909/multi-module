package com.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinRequest {

    private String nickname;
    private String email;
    private String password;

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
