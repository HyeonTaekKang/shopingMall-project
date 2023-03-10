package com.homeshoping.homeshoping.repository.member;

import com.homeshoping.homeshoping.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndPassword(String email , String password);
}
