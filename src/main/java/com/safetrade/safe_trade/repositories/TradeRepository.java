package com.safetrade.safe_trade.repositories;

import com.safetrade.safe_trade.entities.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TradeRepository extends JpaRepository<Trade, Long>, QuerydslPredicateExecutor<Trade> {
    // <Trade, 기본키> 조건식을 많이 쓸수 있으므로 Querydsl... -> models로 이동(save)

}
