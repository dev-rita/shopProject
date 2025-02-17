package com.shop.project.order.service;

import com.shop.project.order.dto.OrderRequest;
import com.shop.project.order.dto.OrderRead;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    // 주문 목록 조회
    Page<OrderRead> findAll(Integer memberId, Pageable pageable);

    // 주문 상세 조회
    OrderRead find(Integer id, Integer memberId);

    // 주문 내역 저장
    void order(OrderRequest params, Integer memberId);
}