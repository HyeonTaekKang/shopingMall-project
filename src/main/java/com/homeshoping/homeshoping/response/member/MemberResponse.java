package com.homeshoping.homeshoping.response.member;

import com.homeshoping.homeshoping.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private String name; // 이름

    private String email; // 이메일

    private AddressResponse addressResponse; // 주소

    @Builder
    public MemberResponse(String name, String email, AddressResponse addressResponse) {
        this.name = name;
        this.email = email;
        this.addressResponse = addressResponse;
    }

    // 생성자 오버로딩
    @Builder
    public MemberResponse(Member m) {
        this.name = m.getName();
        this.email = m.getEmail();
        this.addressResponse = AddressResponse.builder()
                .city(m.getAddress().getCity())
                .street(m.getAddress().getStreet())
                .zipcode(m.getAddress().getZipcode())
                .build();
    }

}
