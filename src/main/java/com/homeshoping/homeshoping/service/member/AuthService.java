package com.homeshoping.homeshoping.service.member;

import com.homeshoping.homeshoping.Exception.InvalidSigninInformation;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.request.member.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public void signIn(Login login){
        memberRepository.findByEmailAndPassword(login.getEmail(), login.getPassword()).orElseThrow(() -> new InvalidSigninInformation());
    }
}
