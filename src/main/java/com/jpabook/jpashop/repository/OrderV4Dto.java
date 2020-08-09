package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderV4Dto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private Address address;

    public OrderV4Dto(Long orderId, String name, LocalDateTime orderDateTime, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
