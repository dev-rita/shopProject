package com.shop.project.item.service;

import com.shop.project.item.dto.ItemRead;
import java.util.List;

public interface ItemService {

    // 전체 상품 목록 조회
    List<ItemRead> findAll();

    // 상품 목록 조회(특정 아이디 리스트로 조회)
    List<ItemRead> findAll(List<Integer> ids);
}