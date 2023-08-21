package com.example.bitcamptiger.userOrder.controller;

import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;
import com.example.bitcamptiger.userOrder.service.UserOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/UserOrders")
public class UserOrderController {
    //주문 생성과 주문 조회를 위한 엔드포인트

    private final UserOrderService userOrderService;

    public UserOrderController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    //주문을 넣는 컨트롤러
    @PostMapping("/createOrder")
    public ResponseEntity<UserOrderDTO> createOrder(@RequestBody UserOrderDTO orderDTO) {
        UserOrderDTO createdOrder = userOrderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
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
