package com.example.bitcamptiger.cart.entity;

import com.example.bitcamptiger.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;



//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CartItem> cartItems = new ArrayList<>();


    //처음 장바구니에 상품을 담을 때는 해당 회원의 장바구니를 생성해야 함.
    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }


}
