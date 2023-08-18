package com.example.bitcamptiger.cart.repository;


import com.example.bitcamptiger.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMemberId(Long memberId);


}
