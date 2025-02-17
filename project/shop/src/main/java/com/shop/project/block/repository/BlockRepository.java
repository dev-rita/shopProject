package com.shop.project.block.repository;

import com.shop.project.block.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Integer> {

    // 토큰 차단 데이터 조회
    Optional<Block> findByToken(String token);
}