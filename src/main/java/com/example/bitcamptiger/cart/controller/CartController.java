package com.example.bitcamptiger.cart.controller;

import com.example.bitcamptiger.cart.dto.CartDTO;
import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.repository.CartRepository;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    public final CartService cartService;
    public final CartRepository cartRepository;
    public final MemberRepository memberRepository;
    public final MenuRepository menuRepository;

//    @GetMapping("/cartmenu")
//    public ResponseEntity<?> getCartMenuList(CustomUserDetails userDetails){
//        ResponseDTO<CartDTO> response = new ResponseDTO<>();
//        try{
//            List<CartDTO> cartDTOList = cartService.getCartList();
//
//            response.setItemlist(cartDTOList);
//            response.setStatusCode(HttpStatus.OK.value());
//
//            return ResponseEntity.ok().body(response);
//        }catch(Exception e) {
//            response.setErrorMessage(e.getMessage());
//            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }


    @PostMapping("/insertCart")
    public ResponseEntity<?> insertCart(CartItemDTO cartItemDTO){

        ResponseDTO<CartDTO> response = new ResponseDTO<>();

        try{
            cartService.insertCart(cartItemDTO);

            List<CartDTO> cartDTOList = cartService.getCartList(cartItemDTO.getId());

            response.setItemlist(cartDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }




}
