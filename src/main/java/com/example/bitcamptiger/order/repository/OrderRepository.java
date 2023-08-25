package com.example.bitcamptiger.order.repository;

import com.example.bitcamptiger.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByMemberId(Long memberId);


}
