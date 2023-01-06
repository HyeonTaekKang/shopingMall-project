package com.homeshoping.homeshoping.entity.delivery;

import com.homeshoping.homeshoping.entity.address.Address;
import com.homeshoping.homeshoping.entity.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // [ READY , DELIVER ]

    @Embedded
    private Address address;

    @OneToOne( mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Builder
    public Delivery(Long id, DeliveryStatus deliveryStatus, Address address, Order order) {
        this.id = id;
        this.deliveryStatus = deliveryStatus;
        this.address = address;
        this.order = order;
    }

    // 배달 정보 생성 메서드
    public static Delivery createDelivery(Address address){
        Delivery delivery = new Delivery();

        delivery.deliveryStatus = DeliveryStatus.READY;

        delivery.address = Address.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .build();

        return delivery;
    }

    // Order와 연관관계 맺기
    public void setOrder(Order order){
        this.order = order;
    }
}
