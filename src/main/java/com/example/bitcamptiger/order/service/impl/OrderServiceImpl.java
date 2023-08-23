package com.example.bitcamptiger.order.service.impl;

import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.order.dto.OrderDTO;
import com.example.bitcamptiger.order.dto.OrderMenuDTO;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.order.entity.OrderMenu;
import com.example.bitcamptiger.order.repository.OrderRepository;
import com.example.bitcamptiger.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    //실제 주문 생성과 조회 로직을 구현
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final CartService cartService;

    

    //주문 정보와 회원, 메뉴 정보를 바탕으로 새로운 주문을 생성
    // 새 주문 생성 로직
    @Override
    public Orders createOrder(OrderDTO orderDTO) {

        // 주문한 회원 ID를 이용하여 Member 엔티티 조회
        Member member = memberRepository.findById(orderDTO.getMember().getId())
                .orElseThrow(() -> new RuntimeException("Member 확인 오류"));


        // 멤버의 ID를 기반으로 장바구니 아이템들을 조회
        List<CartItemDTO> cartItemList = cartService.getCartList(orderDTO.getMember());

        List<OrderMenu> orderMenuList = new ArrayList<>();
        List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();

        int totalPrice = 0;
        int totalQuantity = 0;

        //장바구니의 각 아이템에 해당하는 메뉴를 조회
        for(CartItemDTO cartItemDTO : cartItemList){

            // 장바구니의 각 아이템에 대한 메뉴 조회
            Menu menu = menuRepository.findById(orderDTO.getMenu().getId())
                    .orElseThrow(() -> new RuntimeException("Menu 확인 오류"));

            //장바구니의 각 아이템에 대한 OrderMenu 객체 생성
            OrderMenu orderMenu = OrderMenu.createOrderMenu(menu, cartItemDTO.getCartQuantity());
            orderMenuList.add(orderMenu);

            totalPrice += orderMenu.getMenuPrice();
            totalQuantity += orderMenu.getQuantity();

            orderMenuDTOList.add(OrderMenuDTO.of(orderMenu));


        }

        orderDTO.setOrderedMenuDTOList(orderMenuDTOList);
        orderDTO.setTotalPrice(totalPrice);
        orderDTO.setTotalQuantity(totalQuantity);

        Orders order = Orders.createOrder(member, orderMenuList);

        orderRepository.save(order);

        //주문 완료된 menu 장바구니에서 비우기
        cartService.clearCart(orderDTO.getMenu().getId());

        return order;
    }



    // 멤버 ID로 주문리스트 조회
    @Override
    public List<OrderDTO> getOrderList(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow();
        List<Orders> orderList = orderRepository.findByMemberId(member.getId());

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for(Orders order : orderList){
            OrderDTO orderDTO = OrderDTO.of(order);

            //각 Order에 대한 OrderMenu 목록을 OrderMenuDTO로 변환
            List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
            for(OrderMenu orderMenu : order.getOrderMenuList()){
                orderMenuDTOList.add(OrderMenuDTO.of(orderMenu));
            }
            orderDTO.setOrderedMenuDTOList(orderMenuDTOList);
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }


    // 주문 상세 내역 확인



    
    //주문 취소






}





