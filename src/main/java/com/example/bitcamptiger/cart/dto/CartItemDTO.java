package com.example.bitcamptiger.cart.dto;


import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

    private Long id;
    private Cart cart;
    private Menu menu;
    private Integer cartQuantity;

    private static ModelMapper modelMapper = new ModelMapper();

    public CartItem createCartItem(){
        return modelMapper.map(this, CartItem.class);
    }

    public static CartItemDTO of(CartItem cartItem){
        return modelMapper.map(cartItem, CartItemDTO.class);
    }
}
