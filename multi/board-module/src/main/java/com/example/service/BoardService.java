package com.example.service;

import com.example.domain.Board;
import com.example.dto.BoardSaveRequest;
import com.example.jwt.TokenInfo;
import com.example.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

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
    public List<Board> findAllBoard() {

        return boardRepository.findAll();
    }

}
