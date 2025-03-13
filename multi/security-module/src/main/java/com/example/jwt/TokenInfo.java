package com.example.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenInfo implements TokenInterface {

    private Long id;
    private String nickname;
    private String email;

}
