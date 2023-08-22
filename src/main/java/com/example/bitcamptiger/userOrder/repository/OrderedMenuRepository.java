package com.example.bitcamptiger.userOrder.repository;

import com.example.bitcamptiger.userOrder.entity.OrderedMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedMenuRepository extends JpaRepository<OrderedMenu, Long> {
}
