package com.example.bitcamptiger.userOrder.dto;

import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDTO {

    private Long memberId; // 주문한 회원 ID
    private Long menuId;   // 주문한 메뉴 ID
    private int quantity;   // 주문의 메뉴별 수량(->떡볶이면 떡볶이 개수가 몇개인지...)
    private int price;      // 주문한 메뉴의 가격
    private int totalAmount; // 총 결제 금액
    private VendorDTO vendor; // 해당 메뉴를 제공하는 가게 정보

    private int menuTotalAmount; // 메뉴별 합산 금액 (->같은메뉴별로 얼마나왔는지)
    private int totalQuantity; // 주문 메뉴수량을 합산한 총 주문메뉴수량 (->주문메뉴 총수량)

    private Cart cart;


    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }



}