package com.example.bitcamptiger.cart.controller;

import com.example.bitcamptiger.cart.dto.CartDTO;
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
import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;
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


    // 장바구니에 메뉴 추가
    @PostMapping("/addCart")
    public ResponseEntity<ResponseDTO<CartItemDTO>> addMenuToCart(@RequestBody CartItemDTO cartItemDTO) {
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();

        try {
            Member member = memberRepository.findById(cartItemDTO.getCart().getMember().getId())
                    .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
            Menu menu = menuRepository.findById(cartItemDTO.getMenu().getId())
                    .orElseThrow(() -> new RuntimeException("메뉴 정보를 찾을 수 없습니다."));

            // 회원과 메뉴 정보를 이용하여 장바구니에 메뉴를 추가하고 업데이트된 장바구니 정보를 가져옴.
            Cart updatedCart = cartService.addCart(member, menu, cartItemDTO.getCartQuantity());
            // 업데이트된 장바구니 정보로부터 장바구니에 담긴 메뉴들을 조회
            List<CartItemDTO> cartItemDTOList = cartService.getCartList(updatedCart);

            response.setItemlist(cartItemDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //장바구니 수량 수정



    //장바구니 menu 삭제
    @DeleteMapping("/deletecartitem")
    public ResponseEntity<?> deleteCartItem(@RequestBody CartItemDTO cartItemDTO){
        System.out.println(cartItemDTO);
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();

        try{
            cartService.deleteCartItem(cartItemDTO.getCart().getId(), cartItemDTO.getMenu().getId());

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
    @DeleteMapping("/info")
    public ResponseEntity<?> deleteCart(@RequestBody CartItemDTO cartItemDTO){
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();

        try{
            cartService.deleteCart(cartItemDTO);

            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



}
