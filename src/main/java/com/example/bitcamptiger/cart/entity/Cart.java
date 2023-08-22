package com.example.bitcamptiger.cart.entity;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.userOrder.entity.UserOrder;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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


    @OneToMany(mappedBy = "cart")
    @JsonManagedReference   //순환참조 문제를 해결하기 위해 주관리자 명시
    private List<CartItem> cartItems = new ArrayList<>();


    //처음 장바구니에 상품을 담을 때는 해당 회원의 장바구니를 생성해야 함.
    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private UserOrder userOrder;



}
