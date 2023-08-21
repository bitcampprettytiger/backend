package com.example.bitcamptiger.cart.controller;

import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.cart.repository.CartItemRepository;
import com.example.bitcamptiger.cart.repository.CartRepository;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    public final CartService cartService;
    public final CartRepository cartRepository;
    public final CartItemRepository cartItemRepository;
    public final MemberRepository memberRepository;
    public final MenuRepository menuRepository;

    //내 장바구니 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getMyCart(@PathVariable Long memberId){
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();
        try{
            Member member = memberRepository.findById(memberId).orElseThrow();
            Cart cart = cartRepository.findByMemberId(member.getId());

            List<CartItemDTO> cartItemDTOList = cartService.getCartList(cart);

            response.setItemlist(cartItemDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    //장바구니에 메뉴 추가
    @PostMapping("/info")
    public ResponseEntity<?> addCart(@RequestBody CartItemDTO cartItemDTO){
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();

        try{
            Member member = memberRepository.findById(cartItemDTO.getCart().getMember().getId()).orElseThrow();

            Menu menu = menuRepository.findById(cartItemDTO.getMenu().getId()).orElseThrow();

            Cart cart = cartService.addCart(member, menu, cartItemDTO.getCartQuantity());

            List<CartItemDTO> cartItemDTOList = cartService.getCartList(cart);

            response.setItemlist(cartItemDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //장바구니 수량 수정



    //장바구니 menu 삭제
    @DeleteMapping("/info/{cartId}/{menuId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId, @PathVariable Long menuId, CartItemDTO cartItemDTO){
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();

        try{
            cartService.deleteCartItem(cartId, menuId);

            List<CartItemDTO> cartItemDTOList = cartService.getCartList(cartItemDTO.getCart());

            response.setItemlist(cartItemDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    //장바구니 menu 전체 삭제



}
