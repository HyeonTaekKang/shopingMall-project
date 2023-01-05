package com.homeshoping.homeshoping.entity.delivery;

import com.homeshoping.homeshoping.entity.member.Address;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // [ READY , DELIVER ]

    @Embedded
    private Address address;
}
