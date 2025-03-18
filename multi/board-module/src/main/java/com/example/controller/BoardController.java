package com.example.controller;

import com.example.annotation.CurrentMember;
import com.example.dto.request.BoardSaveRequest;
import com.example.jwt.TokenInfo;
import com.example.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 저장
    @PostMapping
    public ResponseEntity<?> saveBoard(@CurrentMember TokenInfo tokenInfo, @RequestBody BoardSaveRequest request) {

        boardService.saveBoard(tokenInfo, request);

        return ResponseEntity
                .ok()
                .build();
    }

    // 게시글 단건 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> findBoard(@PathVariable Long boardId) {

        return ResponseEntity
                .ok(boardService.findBoard(boardId));
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<?> findAllBoard(@CurrentMember TokenInfo tokenInfo) {

        return ResponseEntity
                .ok(boardService.findAllBoard(tokenInfo));
    }

}
