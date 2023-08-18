package com.example.bitcamptiger.cart.dto;

import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private Long id;
    private Menu menu;
    private Member member;
    //상품 수량
    private Integer cartQuantity;

    private List<MenuImage> menuImageList;

    private static ModelMapper modelMapper = new ModelMapper();

    public Cart createCart(){
        return modelMapper.map(this, Cart.class);
    }

    public static CartDTO of(Cart cart){
        return modelMapper.map(cart, CartDTO.class);
    }
}
