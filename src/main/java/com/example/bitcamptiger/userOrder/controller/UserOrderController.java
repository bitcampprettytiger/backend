package com.example.bitcamptiger.userOrder.controller;

import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;
import com.example.bitcamptiger.userOrder.entity.OrderedMenu;
import com.example.bitcamptiger.userOrder.entity.UserOrder;
import com.example.bitcamptiger.userOrder.repository.UserOrderRepository;
import com.example.bitcamptiger.userOrder.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/UserOrders")
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;
    private final CartService cartService;
    private final MenuRepository menuRepository;
    private final UserOrderRepository userOrderRepository;


    // 장바구니에 담긴 메뉴들로 주문 생성하는 엔드포인트
    @PostMapping("/createOrderByCartItems")
    public ResponseEntity<List<UserOrderDTO>> createOrderByCartItems(@RequestBody Member member) {
        try {
            List<UserOrderDTO> orderDTOList = new ArrayList<>(); // 생성된 주문 목록을 담을 리스트 생성

            List<CartItem> cartItemList = cartService.getCartItemsByMemberId(member.getId()); // 멤버의 ID를 기반으로 장바구니 아이템들을 조회

//           List<OrderedMenu> orderedMenuList = new ArrayList<>();
//            for (CartItem cartItem : cartItemList) {
//                // CartItemDTO에 있는 menu 속성을 통해 메뉴 정보 가져오기
//                OrderedMenu orderedMenu = new OrderedMenu();
//                orderedMenu.setMenu(cartItem.getMenu());
//                orderedMenu.setQuantity(cartItem.getCartQuantity());
//                orderedMenuList.add(orderedMenu);
//            }

            userOrderService.createOrderFromCartItem(member);

            return ResponseEntity.ok(orderDTOList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build(); // 예외 발생 시 클라이언트에 BadRequest 상태로 응답
        }
    }

    // 주문 ID로 주문 조회하는 엔드포인트
    @GetMapping("/{orderId}")
    public ResponseEntity<UserOrderDTO> getOrder(@PathVariable Long orderId) {
        UserOrderDTO orderDTO = userOrderService.getOrderById(orderId);
        if (orderDTO != null) {
            return ResponseEntity.ok(orderDTO);
        } else {
            return ResponseEntity.notFound().build(); // 주문이 존재하지 않을 경우 NotFound 상태로 응답
        }
    }
}
