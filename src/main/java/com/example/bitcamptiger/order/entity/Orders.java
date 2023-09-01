package com.example.bitcamptiger.order.entity;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.payments.entity.Payments;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id; // 주문 고유 번호 값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 주문한 회원

    @Column
    private LocalDateTime orderDate;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderMenu> orderMenuList = new ArrayList<>(); // 주문한 메뉴들의 정보

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payments payments;

    ///////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
    //////
    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenuList.add(orderMenu);
        orderMenu.setOrder(this);
    }


    public static Orders createOrder(Member member, List<OrderMenu> orderMenuList){
        Orders order = new Orders();
        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());

        for(OrderMenu orderMenu : orderMenuList){
            order.addOrderMenu(orderMenu);
        }

        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderMenu orderMenu : orderMenuList){
            totalPrice += orderMenu.getMenuPrice();
        }
        return totalPrice;
    }

    public int getTotalQuantity(){
        int totalQuantity = 0;
        for(OrderMenu orderMenu : orderMenuList){
            totalQuantity += orderMenu.getQuantity();
        }
        return totalQuantity;
    }



}


