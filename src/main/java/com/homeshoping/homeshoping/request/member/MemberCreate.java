package com.homeshoping.homeshoping.request.member;

import com.homeshoping.homeshoping.entity.member.Address;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embedded;

@Getter
public class MemberCreate {

    private Long id;

    private String name;

    private String email;

    private String password;

    private AddressCreate addressCreate; // 주소

    @Builder
    public MemberCreate(Long id, String name, String email, String password, AddressCreate addressCreate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.addressCreate = addressCreate;
    }
}
