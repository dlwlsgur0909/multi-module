package com.example.service;

import com.example.domain.Board;
import com.example.dto.request.BoardSaveRequest;
import com.example.dto.response.BoardResponse;
import com.example.dto.response.MemberInfoResponse;
import com.example.jwt.TokenInfo;
import com.example.repository.BoardRepository;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    private final RestClient memberRestClient;

    // 게시글 저장
    @Transactional
    public void saveBoard(final TokenInfo tokenInfo, final BoardSaveRequest request) {

        boardRepository.save(request.toEntity(tokenInfo.getId()));
    }

    // 게시글 단건 조회
    public Board findBoard(final Long boardId) {

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));
    }

    // 게시글 전체 조회
    public List<BoardResponse> findAllBoard(final TokenInfo tokenInfo) {

        List<Board> boardList = boardRepository.findAll();

        List<Long> memberIdList = boardList.stream()
                .map(Board::getMemberId)
                .toList();

        List<MemberInfoResponse> memberList = memberRestClient.get()
                .uri(uriBuilder -> {
                    return uriBuilder.path("/api/members/idList")
                            .queryParam("memberIdList", memberIdList)
                            .build();
                })
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenInfo.getAccessToken())
                .exchange((clientRequest, clientResponse) -> {
                    if (!clientResponse.getStatusCode().equals(HttpStatus.OK)) {
                        throw new InternalServerErrorException("Member 정보 호출 API 실패");
                    }

                    return clientResponse.bodyTo(new ParameterizedTypeReference<>() {
                    });
                });

        return boardList.stream()
                .map(board -> {
                    assert memberList != null;
                    String nickname = memberList.stream()
                            .filter(member -> member.getMemberId().equals(board.getMemberId()))
                            .findFirst()
                            .orElse(MemberInfoResponse.builder().nickname("작성자 없음").build())
                            .getNickname();

                    return BoardResponse.builder()
                            .boardTitle(board.getTitle())
                            .boardContent(board.getContent())
                            .nickname(nickname)
                            .build();
                })
                .toList();
    }

}
