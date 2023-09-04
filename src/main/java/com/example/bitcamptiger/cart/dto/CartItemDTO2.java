package com.example.bitcamptiger.cart.dto;


import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.dto.MenuImageDTO;
import com.example.bitcamptiger.menu.entity.Menu;
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
public class CartItemDTO2 {

    private Long id;
    private Long cart;
    private Long menu;
    private Integer cartQuantity;

    private List<MenuImageDTO> menuImageDTOList;

    private static ModelMapper modelMapper = new ModelMapper();

    public CartItem createCartItem(){
        return modelMapper.map(this, CartItem.class);
    }

    public static CartItemDTO2 of(CartItem cartItem){
        return modelMapper.map(cartItem, CartItemDTO2.class);
    }
}
