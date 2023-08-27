package com.example.bitcamptiger.order.dto;

import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.order.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderMenuDTO {

//    private Long menuId; // 주문한 메뉴의 ID
    private Menu menu;
    private int quantity; // 주문 수량
//    private int price; // 메뉴 가격


    private static ModelMapper modelMapper = new ModelMapper();

    public OrderMenu createOrderMenu(){
        return modelMapper.map(this, OrderMenu.class);
    }

    public static OrderMenuDTO of(OrderMenu orderMenu){
        return modelMapper.map(orderMenu, OrderMenuDTO.class);
    }

}
