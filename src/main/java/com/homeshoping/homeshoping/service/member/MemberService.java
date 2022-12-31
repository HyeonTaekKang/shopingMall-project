package com.homeshoping.homeshoping.service.member;

import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    public void join(MemberCreate memberCreate){
        memberRepository.save(new Member(memberCreate));
    }
}
