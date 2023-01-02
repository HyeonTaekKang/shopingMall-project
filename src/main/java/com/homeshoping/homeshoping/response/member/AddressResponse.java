package com.homeshoping.homeshoping.response.member;

import com.homeshoping.homeshoping.entity.member.Address;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressResponse {

    private String city;
    private String street;
    private String zipcode;

    @Builder
    public AddressResponse(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    // 생성자 오버로딩
    @Builder
    public AddressResponse(Address address) {
        this.city = address.getCity();
        this.street = address.getStreet();
        this.zipcode = address.getZipcode();
    }




}
