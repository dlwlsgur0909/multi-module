package com.example.service;

import com.example.domain.Member;
import com.example.dto.response.MemberInfoResponse;
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
    @Transactional
    public void updateMember(Long id, Member member) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "에 해당하는 Member를 찾을 수 없습니다"));

        findMember.update(member);
    }

    // Member 삭제
    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    /* Board-Module에서 호출하는 API */

    // Member Id 목록으로 조회
    public List<MemberInfoResponse> findAllMemberByIdList(final List<Long> memberIdList) {

        return memberRepository.findAllByIdList(memberIdList)
                .stream()
                .map(MemberInfoResponse::new)
                .toList();
    }

    // 작성자 정보 조회
    public MemberInfoResponse findMemberForBoard(Long id) {

        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "에 해당하는 Member를 찾을 수 없습니다"));

        return new MemberInfoResponse(findMember);
    }

}
