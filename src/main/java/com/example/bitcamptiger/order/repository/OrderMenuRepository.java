package com.example.bitcamptiger.order.repository;

import com.example.bitcamptiger.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
