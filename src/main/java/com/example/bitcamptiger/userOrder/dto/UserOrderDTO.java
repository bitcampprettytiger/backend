package com.example.bitcamptiger.userOrder.dto;

import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDTO {

    private Long memberId; // 주문한 회원 ID
    private Long menuId;   // 주문한 메뉴 ID
    private int quantity;   // 주문의 메뉴별 수량(->떡볶이면 떡볶이 개수가 몇개인지...)
    private int price;      // 주문한 메뉴의 가격
    private int allTotalAmount; // 총 결제 금액
    private VendorDTO vendor; // 해당 메뉴를 제공하는 가게 정보

    private int menuTotalAmount; // 메뉴별 합산 금액 (->같은메뉴별로 얼마나왔는지)
    private int totalQuantity; // 주문 메뉴수량을 합산한 총 주문메뉴수량 (->주문메뉴 총수량)

    private Cart cart;


    private List<OrderedMenuDTO> orderedMenus;

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    //메뉴 수량과 가격을 곱하여 계산
    //주문이 생성되거나 수정될 때 호출하면 menuTotalAmount가 자동으로 계산
    public void calculateMenuTotalAmount() {
        this.menuTotalAmount = this.quantity * this.price;
    }

    //수량 또는 가격이 변경되었을 때 호출하면 해당 변경사항을 바탕으로 menuTotalAmount를 갱신
    public void updateMenuTotalAmount() {
        if (this.quantity > 0 && this.price > 0) {
            this.menuTotalAmount = this.quantity * this.price;
        } else {
            this.menuTotalAmount = 0;
        }
    }

    // 총 결제 금액 계산
    public void calculateAllTotalAmount() {
        this.allTotalAmount = this.menuTotalAmount * this.totalQuantity;
    }
}
