package com.example.bitcamptiger.userOrder.controller;

import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;
import com.example.bitcamptiger.userOrder.service.UserOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/UserOrders")
public class UserOrderController {
    //주문 생성과 주문 조회를 위한 엔드포인트

    private final UserOrderService userOrderService;
    private final CartService cartService;
    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;

    public UserOrderController(UserOrderService userOrderService, CartService cartService, MenuRepository menuRepository, MemberRepository memberRepository) {
        this.userOrderService = userOrderService;
        this.cartService = cartService;
        this.menuRepository = menuRepository;
        this.memberRepository = memberRepository;
    }

    // 장바구니에 담긴 메뉴들로 주문 생성하는 컨트롤러
    @PostMapping("/createOrderByCartItems")
    public ResponseEntity<List<UserOrderDTO>> createOrderByCartItems(@RequestBody List<CartItemDTO> cartItems) {
        try {
            // 생성된 주문 목록을 담을 리스트 생성
            List<UserOrderDTO> orderDTOList = new ArrayList<>();

            // 장바구니에 담긴 각 메뉴 아이템에 대해 처리
            for (CartItemDTO cartItem : cartItems) {
                // 해당 메뉴 아이템의 회원 정보를 조회
                Member member = memberRepository.findById(cartItem.getCart().getMember().getId())
                        .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

                // 해당 메뉴 아이템의 메뉴 정보를 조회
                Menu menu = menuRepository.findById(cartItem.getMenu().getId())
                        .orElseThrow(() -> new RuntimeException("메뉴 정보를 찾을 수 없습니다."));

                // 주문을 생성하고 그 결과를 주문 DTO로 받아옴
                UserOrderDTO orderDTO = userOrderService.createOrderFromCartItem(member, menu, cartItem.getCartQuantity());

                // 생성된 주문 DTO를 리스트에 추가
                orderDTOList.add(orderDTO);
            }

            // 생성된 주문 DTO 리스트를 클라이언트에 응답으로 전송
            return ResponseEntity.ok(orderDTOList);
        } catch (Exception e) {
            // 예외 발생 시 클라이언트에 BadRequest 상태로 응답
            return ResponseEntity.badRequest().build();
        }
    }

    //넣은 주문을 확인하는 컨트롤러
    @GetMapping("/{orderId}")
    public ResponseEntity<UserOrderDTO> getOrder(@PathVariable Long orderId) {
        UserOrderDTO orderDTO = userOrderService.getOrderById(orderId);
        if (orderDTO != null) {
            return ResponseEntity.ok(orderDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
