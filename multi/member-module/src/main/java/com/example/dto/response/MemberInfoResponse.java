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
public class MemberInfoResponse {

    private Long memberId;
    private String nickname;

    public MemberInfoResponse(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
    }
}
