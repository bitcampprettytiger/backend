package com.example.bitcamptiger.userOrder.entity;

import com.example.bitcamptiger.menu.entity.Menu;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderedMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_order_id")
    @JsonBackReference
    private UserOrder userOrder; // 부모 주문 엔티티와의 관계 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu; // 주문한 각각의 메뉴

    private int quantity; // 주문한 메뉴 각각의 수량
    private int price; // 주문한 메뉴각각의 가격

    // 메뉴별 총 금액 계산
    public int getTotalAmount() {
        return quantity * price;
    }

}

