package com.example.bitcamptiger.userOrder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderedMenuDTO {
    private Long menuId; // 주문한 메뉴의 ID
    private int quantity; // 주문 수량
    private int price; // 메뉴 가격
    private int totalAmount; // 메뉴별 총 금액

}
