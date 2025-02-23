package com.shop.project.order.repository;

import com.shop.project.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    // 주문 상품 목록 조회
    List<OrderItem> findAllByOrderId(Integer orderId);
}