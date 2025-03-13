package com.example.controller;

import com.example.domain.Member;
import com.example.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> saveMember(@RequestBody Member member) {

        memberService.saveMember(member);

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findMember(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .body(memberService.findMember(id));
    }

    @GetMapping
    public ResponseEntity<?> findAllMember() {

        return ResponseEntity
                .ok()
                .body(memberService.findAllMember());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id,
                                          @RequestBody Member member) {

        memberService.updateMember(id, member);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

    /* Security 모듈에서 호출하는 API */

    @GetMapping("/auth/email")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {

        return ResponseEntity
                .ok()
                .body(memberService.findByEmail(email));
    }

    @PostMapping("/auth/join")
    public ResponseEntity<?> joinMember(@RequestBody Member member) {

        memberService.saveMember(member);

        return ResponseEntity
                .ok()
                .build();
    }

}
