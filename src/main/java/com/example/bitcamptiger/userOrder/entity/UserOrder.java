package com.example.bitcamptiger.userOrder.entity;

import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.IdentifiableType;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 주문 고유 번호 값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 주문한 회원

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderedMenu> orderedMenuList = new ArrayList<>(); // 주문한 메뉴들의 정보

    private int totalAmount; // 총 결제 금액

    private int totalQuantity; // 주문 메뉴 수량을 합산한 총 주문 메뉴 수량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id") // 가게 정보를 참조하는 외래키
    private Vendor vendor; // 해당 메뉴를 제공하는 가게 정보

//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_id")
//    private Cart cart;

    // 수정된 부분: OrderedMenu에 있는 메뉴 정보 가져오기
    }


