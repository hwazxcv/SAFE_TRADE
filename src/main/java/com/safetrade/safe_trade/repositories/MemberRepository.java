package com.safetrade.safe_trade.repositories;


import com.safetrade.safe_trade.entities.Member;
import com.safetrade.safe_trade.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    Optional<Member> findByEmail(String email);

    /**
     * 등록된 회원 여부 체크
     *
     * @param email
     * @return
     */
    default boolean exists(String email) {
        return exists(QMember.member.email.eq(email));
    }
}
