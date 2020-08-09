package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> searchOrder(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" +
                    orderSearch.getMemberName() + "%"); criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건

        return query.getResultList();
    }

    public List<Order> searchOrdersWithFetch() {
        return em.createQuery(
                "select o from Order o " +
                        "join fetch o.member " +
                        "join fetch o.delivery", Order.class)
                .getResultList();
    }

    public List<OrderV4Dto> searchOrderWithDto() {
        return em.createQuery(
                "select new com.jpabook.jpashop.repository.OrderV4Dto(" +
                        "   o.id," +
                        "   m.name," +
                        "   o.orderDateTime," +
                        "   o.status," +
                        "   o.delivery.address) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d", OrderV4Dto.class)
                .getResultList();

    }

    public List<Order> searchOrderWithItem(OrderSearch orderSearch) {
        return em.createQuery(
                "select o " +
                        "from Order o " +
                        "join fetch o.member " +
                        "join fetch o.delivery ", Order.class)
                .setFirstResult(1)
                .setMaxResults(100)
                .getResultList();
    }
}
