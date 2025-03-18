package com.example.repository;

import com.example.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(
            """
            SELECT
                member
            FROM
                Member member
            WHERE member.id IN (:memberIdList)
            """
    )
    List<Member> findAllByIdList(List<Long> memberIdList);
}
