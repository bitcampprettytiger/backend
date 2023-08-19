package com.example.bitcamptiger.cart.repository;


import com.example.bitcamptiger.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

   Cart findByMemberId(Long memberId);


}
