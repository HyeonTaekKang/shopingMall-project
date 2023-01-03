package com.homeshoping.homeshoping.service.member;

import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.response.member.MemberResponse;
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


    // 회원정보 가져오기
//    public MemberResponse find(Long memberId){
//
//        // member 찾아오고
//        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFound());
//
//        // 엔티티 -> DTO로 변경해서 리턴
//        return new Member;
//    }
}
