package com.homeshoping.homeshoping.request.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressCreate {

    private String city;
    private String street;
    private String zipcode;

    @Builder
    public AddressCreate(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
