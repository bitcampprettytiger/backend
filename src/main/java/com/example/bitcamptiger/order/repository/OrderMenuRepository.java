package com.example.bitcamptiger.order.repository;

import com.example.bitcamptiger.order.entity.OrderMenu;
import com.example.bitcamptiger.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

    List<OrderMenu> findByOrder(Orders orders);
}
