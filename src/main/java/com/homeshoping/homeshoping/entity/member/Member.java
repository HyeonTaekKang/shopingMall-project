package com.homeshoping.homeshoping.entity.member;

import com.homeshoping.homeshoping.entity.address.Address;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String name; // 이름

    private String email; // 이메일

    private String password; // 패스워드

    @Embedded
    private Address address; // 주소

    @Builder
    public Member(Long id, String name, String email, String password, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    // member 생성 메서드
    public static Member createMember(MemberCreate memberCreate){
        Member member = new Member();

        member.name = memberCreate.getName();
        member.email = memberCreate.getEmail();
        member.password = memberCreate.getPassword();
        member.address = Address.builder()
                .city(memberCreate.getAddressCreate().getCity())
                .street(memberCreate.getAddressCreate().getStreet())
                .zipcode(memberCreate.getAddressCreate().getZipcode())
                .build();

        return member;
    }


    // 생성자 오버로딩 ( Member 추가 )
    public Member(MemberCreate memberCreate){
        this.name = memberCreate.getName();
        this.email = memberCreate.getEmail();
        this.password = memberCreate.getPassword();
        this.address = Address.builder()
                .city(memberCreate.getAddressCreate().getCity())
                .street(memberCreate.getAddressCreate().getStreet())
                .zipcode(memberCreate.getAddressCreate().getZipcode())
                .build();
    }
}

