package com.example.service;

import com.example.domain.Board;
import com.example.dto.request.BoardSaveRequest;
import com.example.dto.response.BoardResponse;
import com.example.dto.response.MemberInfoResponse;
import com.example.enumeration.ServiceUrl;
import com.example.jwt.TokenInfo;
import com.example.repository.BoardRepository;
import com.example.util.RestClientUtil;
import com.example.util.UrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    private final RestClientUtil restClientUtil;

    // 게시글 저장
    @Transactional
    public void saveBoard(final TokenInfo tokenInfo, final BoardSaveRequest request) {

        boardRepository.save(request.toEntity(tokenInfo.getId()));
    }

    // 게시글 단건 조회
    public BoardResponse findBoard(final TokenInfo tokenInfo, final Long boardId) {

        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + tokenInfo.getAccessToken());

        UrlRequest urlRequest = UrlRequest.builder()
                .serviceUrl(ServiceUrl.MEMBER_MODULE)
                .path("/api/members/" + findBoard.getMemberId() + "/board")
                .headers(headers)
                .build();

        MemberInfoResponse findMember = restClientUtil.getMono(urlRequest, MemberInfoResponse.class);

        return BoardResponse.builder()
                .boardTitle(findBoard.getTitle())
                .boardContent(findBoard.getContent())
                .nickname(findMember.getNickname())
                .build();
    }

    // 게시글 전체 조회
    public List<BoardResponse> findAllBoard(final TokenInfo tokenInfo) {

        List<Board> boardList = boardRepository.findAll();

        List<Long> memberIdList = boardList.stream()
                .map(Board::getMemberId)
                .distinct()
                .toList();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("memberIdList", memberIdList);

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + tokenInfo.getAccessToken());

        UrlRequest urlRequest = UrlRequest.builder()
                .serviceUrl(ServiceUrl.MEMBER_MODULE)
                .path("/api/members/idList")
                .queryParams(queryParams)
                .headers(headers)
                .build();

        List<MemberInfoResponse> memberList = restClientUtil.getFlux(urlRequest, MemberInfoResponse.class);

        return boardList.stream()
                .map(board -> {
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
