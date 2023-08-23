package com.example.bitcamptiger.order.entity;

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
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders order; // 부모 주문 엔티티와의 관계 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu; // 주문한 각각의 메뉴

    private int quantity; // 주문한 메뉴 각각의 수량
    private int price; // 주문한 메뉴각각의 가격


    public static OrderMenu createOrderMenu(Menu menu, int quantity){
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.setMenu(menu);
        orderMenu.setQuantity(quantity);
        orderMenu.setPrice(menu.getPrice());
        return orderMenu;
    }


    public int getMenuPrice(){
        return price * quantity;
    }

}

