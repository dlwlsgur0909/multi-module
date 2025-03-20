package com.example.controller;

import com.example.domain.Member;
import com.example.dto.response.MemberInfoResponse;
import com.example.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    // Member 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> findMember(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .body(memberService.findMember(id));
    }

    // Member 전체 조회
    @GetMapping
    public ResponseEntity<?> findAllMember() {

        return ResponseEntity
                .ok()
                .body(memberService.findAllMember());
    }

    // Member 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id,
                                          @RequestBody Member member) {

        memberService.updateMember(id, member);

        return ResponseEntity
                .ok()
                .build();
    }

    // Member 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {

        memberService.deleteMember(id);

        return ResponseEntity
                .ok()
                .build();
    }

    /* Board-Module에서 호출하는 API */

    // Member Id 목록으로 조회
    @GetMapping("/idList")
    public ResponseEntity<?> findAllMemberByIdList(@RequestParam List<Long> memberIdList) {

        return ResponseEntity
                .ok(memberService.findAllMemberByIdList(memberIdList));
    }

    // 작성자 정보 조회
    @GetMapping("/{id}/board")
    public ResponseEntity<?> findMemberForBoard(@PathVariable Long id) {

        return ResponseEntity
                .ok(memberService.findMemberForBoard(id));
    }

}
