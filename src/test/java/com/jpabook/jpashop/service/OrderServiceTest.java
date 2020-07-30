package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    
    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = new Member();
        member.setName("test");
        member.setAddress(new Address("s", "t", "z"));
        em.persist(member);
        
        Item item = new Book();
        item.setStockQuantity(10);
        item.setPrice(500);
        item.setName("ps");
        em.persist(item);
        
        int orderAmt = 5;
        
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderAmt);

        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, order.getStatus(), "상품주문시 상태는 ORDER");

    }

    @Test
    public void 재고수량초과() throws Exception {
        //given
        Member member = new Member();
        member.setName("test");
        member.setAddress(new Address("s", "t", "z"));
        em.persist(member);

        Item item = new Book();
        item.setStockQuantity(10);
        item.setPrice(500);
        item.setName("ps");
        em.persist(item);

        int orderAmt = 20;

        //when

        //then
        assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderAmt));
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = new Member();
        member.setName("test");
        member.setAddress(new Address("s", "t", "z"));
        em.persist(member);

        Item item = new Book();
        item.setStockQuantity(10);
        item.setPrice(500);
        item.setName("ps");
        em.persist(item);

        int orderAmt = 10;

        Long orderId = orderService.order(member.getId(), item.getId(), orderAmt);

        //when
        orderService.cancel(orderId);

        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, order.getStatus());
        assertEquals(item.getStockQuantity(), 10);
    }
}