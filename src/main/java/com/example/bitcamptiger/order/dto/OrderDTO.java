package com.example.bitcamptiger.order.dto;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.order.entity.OrderMenu;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long orderId;

    private Long VendorId;

    @JsonIgnore
    private Member member;

    private String orderDate;   //주문한 날짜

    private int totalQuantity; // 총 주문메뉴 수량
    private int totalPrice; // 총 결제 금액

    private int orderCount;

    private String vendorName;

    private String orderStatus;

    private Vendor vendor; // vendor 엔티티 추가

    private List<OrderMenuDTO> orderedMenuDTOList = new ArrayList<>();


    private static ModelMapper modelMapper = new ModelMapper();

    public Orders createOrder(){
        return modelMapper.map(this, Orders.class);
    }

    public static OrderDTO of(Orders order){
        return modelMapper.map(order, OrderDTO.class);
    }


    public void setOrderMenuList(List<OrderMenuDTO> orderMenuDTOList) {
    }


}
