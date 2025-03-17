package com.example.dto;

import com.example.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardSaveRequest {

    private String boardTitle;
    private String boardContent;

    public Board toEntity(Long memberId) {

        return Board.builder()
                .memberId(memberId)
                .title(boardTitle)
                .content(boardContent)
                .build();
    }

}
