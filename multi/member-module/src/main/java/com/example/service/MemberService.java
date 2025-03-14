package com.example.service;

import com.example.domain.Member;
import com.example.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // Member 단건 조회
    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "에 해당하는 Member를 찾을 수 없습니다"));
    }

    // Member 전체 조회
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    // Member 수정
    public void updateMember(Long id, Member member) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "에 해당하는 Member를 찾을 수 없습니다"));

        findMember.update(member);
    }

    // Member 삭제
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

}
