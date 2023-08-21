package com.example.bitcamptiger.userOrder.repository;

import com.example.bitcamptiger.userOrder.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

}
