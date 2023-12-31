package com.example.bitcamptiger.cart.controller;

import com.example.bitcamptiger.cart.dto.CartDTO;
import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.dto.CartItemDTO2;
import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.cart.repository.CartItemRepository;
import com.example.bitcamptiger.cart.repository.CartRepository;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "getMyCart", description = "내 장바구니 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/info")
    public ResponseEntity<?> getMyCart(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();
        try{

            Member loggedInMember = customUserDetails.getUser(); // 로그인한 사용자 정보에 접근
            Cart cart = cartRepository.findByMemberId(loggedInMember.getId());

            List<CartItemDTO> cartItemDTOList = cartService.getCartList(loggedInMember);
            System.out.println(cartItemDTOList);
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
    @Operation(summary = "addCartItem", description = "장바구니에 메뉴 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/info")
    public ResponseEntity<ResponseDTO<CartItemDTO>> addMenuToCart(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody CartItemDTO2 cartItemDTO2) {
        System.out.println(cartItemDTO2);
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();
//        System.out.println(cartItemDTO);
        try {
            CartItemDTO cartItemDTO  = new CartItemDTO();
//            Cart cart = new Cart();
            Menu menu = new Menu();
            menu.setId(cartItemDTO2.getMenu());
//            cart.setId(cartItemDTO2.getId());
            cartItemDTO.setMenu(menu);
            Member member = customUserDetails.getUser(); // 로그인한 사용자 정보에 접근

            Menu menu2 = menuRepository.findById(cartItemDTO.getMenu().getId())
                    .orElseThrow(() -> new RuntimeException("메뉴 정보를 찾을 수 없습니다."));
            System.out.println("!!!!!!");
//             회원과 메뉴 정보를 이용하여 장바구니에 메뉴를 추가하고 업데이트된 장바구니 정보를 가져옴.
            Cart updatedCart = cartService.addCart(member, menu2, cartItemDTO2.getCartQuantity());
//             업데이트된 장바구니 정보로부터 장바구니에 담긴 메뉴들을 조회
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
    @Operation(summary = "deleteCartItem", description = "장바구니 메뉴 선택 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @DeleteMapping("/deletecartitem")
    public ResponseEntity<?> deleteCartItem(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @RequestParam Long menuId){
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();

        try{
            Member member = customUserDetails.getUser(); // 로그인한 사용자 정보에 접근
            cartService.deleteCartItem(member, menuId);

            List<CartItemDTO> cartItemDTOList = cartService.getCartList(member);

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
    @Operation(summary = "deleteCart", description = "장바구니 메뉴 전체 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @DeleteMapping("/info")
    public ResponseEntity<?> deleteCart(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<CartItemDTO> response = new ResponseDTO<>();

        try{

            Member member = customUserDetails.getUser(); // 로그인한 사용자 정보에 접근

            cartService.deleteCart(member);

            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



}
