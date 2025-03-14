package com.example.jwt;

import com.example.enumeration.Role;
import lombok.*;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenInfo {

    private Long id;
    private String nickname;
    private String email;
    private Role role;

}
