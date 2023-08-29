package com.example.bitcamptiger.order.controller;

import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.order.dto.OrderDTO;
import com.example.bitcamptiger.order.dto.OrderMenuDTO;
import com.example.bitcamptiger.order.entity.OrderMenu;
import com.example.bitcamptiger.order.repository.OrderRepository;
import com.example.bitcamptiger.order.service.OrderService;
import com.querydsl.core.types.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberRepository memberRepository;


    // 장바구니에 담긴 메뉴들로 주문 생성
    @PostMapping("/orderInfo")
    public ResponseEntity<?> createOrderByCartItems(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody OrderDTO orderDTO) {
        ResponseDTO<OrderDTO> response = new ResponseDTO<>();

        try {
            orderDTO.setMember(customUserDetails.getUser());
            orderService.createOrder(orderDTO);

            List<OrderDTO> orderDTOList = orderService.getOrderList(orderDTO.getMember().getId());

            response.setItemlist(orderDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 멤버 ID로 주문리스트 조회
    @GetMapping("/orderInfo/orderList")
    public ResponseEntity<?> getOrderList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<OrderDTO> response = new ResponseDTO<>();

        try{
            Long memberId = customUserDetails.getUser().getId();
            List<OrderDTO> orderDTOList = orderService.getOrderList(memberId);

            response.setItemlist(orderDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        }catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



    //주문 상세 내역 확인

    @GetMapping("/orderDetail/{orderId}")
    public ResponseEntity<?> getOrderDetail(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long orderId){
        ResponseDTO<OrderDTO> response = new ResponseDTO<>();

        try{
            OrderDTO orderDTO = orderService.getOrderDetail(orderId);

            // 주문 상세 내역의 소유자와 현재 로그인한 사용자가 같은지 확인
            if (!orderDTO.getMember().getId().equals(customUserDetails.getUser().getId())) {
                response.setErrorMessage("주문 상세 내역에 접근할 권한이 없습니다.");
                response.setStatusCode(HttpStatus.FORBIDDEN.value());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            response.setItem(orderDTO);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);

        }catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    //주문 취소





}
