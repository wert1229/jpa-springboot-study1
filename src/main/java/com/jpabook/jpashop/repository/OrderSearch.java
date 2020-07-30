package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    String memberName;
    OrderStatus orderStatus;
}
