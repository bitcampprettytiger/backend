package com.example.bitcamptiger.userOrder.entity;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.vendor.entity.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //주문고유번호값

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 주문한 회원

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;     // 주문한 메뉴

    private int quantity;   // 주문 수량

    private int price; // 주문한 메뉴의 가격

    private int totalAmount; // 총 결제 금액

    private int totalQuantity; // 주문 메뉴수량을 합산한 총 주문메뉴수량

    // 메뉴별 합산 금액 및 메뉴별 개수 설정
    private int menuTotalAmount; // 메뉴별 합산 금액

    @ManyToOne
    @JoinColumn(name = "vendor_id") // 가게 정보를 참조하는 외래키
    private Vendor vendor; // 해당 메뉴를 제공하는 가게 정보


    // 메뉴의 가격과 수량을 곱해서 총 금액 계산 및 저장
    public void calculateTotalAmount() {
        totalAmount = menu.getPrice() * quantity;
    }
    //-> A 메뉴를 2개 주문하고, B 메뉴를 3개 주문했다고 가정하면,
    // 각 메뉴의 가격과 수량을 곱해서 나온 금액을 합산하여 전체 주문 금액을 계산할 것.


    // 메뉴별 합산 금액
    public void setMenuTotalAmountAndQuantity() {
        menuTotalAmount = price * quantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }





}
