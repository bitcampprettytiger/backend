package com.example.bitcamptiger.userOrder.service.impl;

import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;
import com.example.bitcamptiger.userOrder.entity.UserOrder;
import com.example.bitcamptiger.userOrder.repository.UserOrderRepository;
import com.example.bitcamptiger.userOrder.service.UserOrderService;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    //실제 주문 생성과 조회 로직을 구현
    private final UserOrderRepository userOrderRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;


    @Autowired
    public UserOrderServiceImpl(UserOrderRepository userOrderRepository, MemberRepository memberRepository, MenuRepository menuRepository) {
        this.userOrderRepository = userOrderRepository;
        this.memberRepository = memberRepository;
        this.menuRepository = menuRepository;
    }

    //주문 정보와 회원, 메뉴 정보를 바탕으로 새로운 주문을 생성
    @Override
    public UserOrderDTO createOrder(UserOrderDTO orderDTO) {

        // memberId를 이용하여 Member 엔티티 조회
        Member member = memberRepository.findById(orderDTO.getMemberId()).orElseThrow(()
                -> new RuntimeException("Member 확인 오류"));

        // menuId를 이용하여 Menu 엔티티 조회
        Menu menu = menuRepository.findById(orderDTO.getMenuId()).orElseThrow(()
                -> new RuntimeException("Menu 확인 오류"));

        // UserOrderDTO를 이용하여 UserOrder 엔티티 생성
        UserOrder order = UserOrder.builder()
                .member(member)
                .menu(menu)
                .quantity(orderDTO.getQuantity())
                .price(menu.getPrice())
                .build();

        // 총 결제 금액 계산
        order.calculateTotalAmount();

        // UserOrderDTO 생성
        UserOrderDTO resultDTO = UserOrderDTO.builder()
                .vendor(orderDTO.getVendor()) // 해당 메뉴를 제공하는 가게 정보
                .memberId(orderDTO.getMemberId()) // 주문한 회원 ID
                .menuId(orderDTO.getMenuId()) // 주문한 메뉴 ID
                .quantity(orderDTO.getQuantity()) // 주문의 메뉴별 수량
                .menuTotalAmount(order.getMenuTotalAmount()) // 메뉴별 합산 금액
                .totalQuantity(orderDTO.getTotalQuantity()) // 총 주문 메뉴 수량
                .totalAmount(order.getTotalAmount()) // 총 결제 금액
                .build();

        return resultDTO;

    }

    //orderId 입력 받아서 주문내역정보 조회
    @Override
    public UserOrderDTO getOrderById(Long orderId) {
        UserOrder order = userOrderRepository.findById(orderId).orElse(null);
        if (order != null) {
            return mapEntityToDTO(order);
        } else {
            throw new RuntimeException("주문 정보가 존재하지 않습니다.");
        }
    }

    @Override
    public UserOrderDTO createOrderFromCartItem(Member member, Menu menu, Integer cartQuantity) {
        try {
            // 주문을 생성하고 그 결과를 주문 DTO로 받아옴
            UserOrderDTO orderDTO = new UserOrderDTO();
            orderDTO.setMemberId(member.getId()); // 주문한 회원 ID
            orderDTO.setMenuId(menu.getId()); // 주문한 메뉴 ID
            orderDTO.setQuantity(cartQuantity); // 주문의 메뉴별 수량
            orderDTO.setPrice(menu.getPrice()); // 주문한 메뉴의 가격

            // 주문 정보를 바탕으로 UserOrder 엔티티 생성
            UserOrder order = UserOrder.builder()
                    .member(member)
                    .menu(menu)
                    .quantity(cartQuantity)
                    .price(menu.getPrice())
                    .build();

            // 총 결제 금액 계산
            order.calculateTotalAmount();

            // UserOrderDTO에 주문 정보 설정
            orderDTO.setMenuTotalAmount(order.getMenuTotalAmount()); // 메뉴별 합산 금액
            orderDTO.setTotalQuantity(cartQuantity); // 총 주문 메뉴 수량
            orderDTO.setTotalAmount(order.getTotalAmount()); // 총 결제 금액

            // 주문한 메뉴를 제공하는 가게 정보를 VendorDTO로 변환하여 설정
            orderDTO.setVendor(VendorDTO.of(menu.getVendor()));

            // 생성된 UserOrderDTO 반환
            return orderDTO;
        } catch (Exception e) {
            throw new RuntimeException("주문 생성 오류: " + e.getMessage());
        }
    }


    private UserOrderDTO mapEntityToDTO(UserOrder order) {
        // UserOrder 엔티티를 UserOrderDTO로 변환하는 로직
            return UserOrderDTO.builder()
                    .memberId(order.getMember().getId()) // 주문한 회원 ID
                    .menuId(order.getMenu().getId()) // 주문한 메뉴 ID
                    .quantity(order.getQuantity()) // 주문의 메뉴별 수량
                    .price(order.getPrice()) // 주문한 메뉴의 가격
                    .totalAmount(order.getTotalAmount()) // 총 결제 금액
                    .menuTotalAmount(order.getMenuTotalAmount()) // 메뉴별 합산 금액
                    .totalQuantity(order.getTotalQuantity()) // 총 주문 메뉴 수량 합산
                    .vendor(VendorDTO.of(order.getVendor())) // 주문한 메뉴를 제공하는 가게 정보를 VendorDTO로 변환하여 설정
                    .build();
        }




}
