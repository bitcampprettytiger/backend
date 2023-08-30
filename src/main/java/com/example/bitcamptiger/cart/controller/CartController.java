package com.example.bitcamptiger.cart.controller;

import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.cart.repository.CartItemRepository;
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
    public final CartItemRepository cartItemRepository;
    public final MemberRepository memberRepository;
    public final MenuRepository menuRepository;

    //내 장바구니 조회
    @GetMapping("/member")
    public ResponseEntity<?> getMyCart(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();
        try{

            Member loggedInMember = customUserDetails.getUser(); // 로그인한 사용자 정보에 접근
            Cart cart = cartRepository.findByMemberId(loggedInMember.getId());

            List<CartItemDTO> cartItemDTOList = cartService.getCartList(loggedInMember);

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
    @PostMapping("/info")

    public ResponseEntity<ResponseDTO<CartItemDTO>> addMenuToCart(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody CartItemDTO cartItemDTO) {

        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();
        System.out.println(cartItemDTO);
        try {

            Member member = customUserDetails.getUser(); // 로그인한 사용자 정보에 접근

            Menu menu = menuRepository.findById(cartItemDTO.getMenu().getId())
                    .orElseThrow(() -> new RuntimeException("메뉴 정보를 찾을 수 없습니다."));

            // 회원과 메뉴 정보를 이용하여 장바구니에 메뉴를 추가하고 업데이트된 장바구니 정보를 가져옴.
            Cart updatedCart = cartService.addCart(member, menu, cartItemDTO.getCartQuantity());
            // 업데이트된 장바구니 정보로부터 장바구니에 담긴 메뉴들을 조회
            List<CartItemDTO> cartItemDTOList = cartService.getCartList(updatedCart.getMember());

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
    public ResponseEntity<?> deleteCartItem(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody CartItemDTO cartItemDTO){
        System.out.println(cartItemDTO);
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();

        try{
            Member loggedInMember = customUserDetails.getUser(); // 로그인한 사용자 정보에 접근
            cartService.deleteCartItem(cartItemDTO.getCart().getId(), cartItemDTO.getMenu().getId());

            List<CartItemDTO> cartItemDTOList = cartService.getCartList(loggedInMember);

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
    public ResponseEntity<?> deleteCart(@AuthenticationPrincipal CustomUserDetails customUserDetails, CartItemDTO cartItemDTO){
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();

        try{

            Member loggedInMember = customUserDetails.getUser(); // 로그인한 사용자 정보에 접근

            cartService.deleteCart(loggedInMember, cartItemDTO);

            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



}
