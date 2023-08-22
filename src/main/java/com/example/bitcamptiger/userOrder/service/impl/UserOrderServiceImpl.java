package com.example.bitcamptiger.userOrder.service.impl;

import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.userOrder.dto.OrderedMenuDTO;
import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;
import com.example.bitcamptiger.userOrder.entity.OrderedMenu;
import com.example.bitcamptiger.userOrder.entity.UserOrder;
import com.example.bitcamptiger.userOrder.repository.UserOrderRepository;
import com.example.bitcamptiger.userOrder.service.UserOrderService;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserOrderServiceImpl implements UserOrderService {
    //실제 주문 생성과 조회 로직을 구현
    private final UserOrderRepository userOrderRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    

    //주문 정보와 회원, 메뉴 정보를 바탕으로 새로운 주문을 생성
    // 새 주문 생성 로직
    @Override
    public UserOrderDTO createOrder(UserOrderDTO orderDTO) {

        // 주문한 회원 ID를 이용하여 Member 엔티티 조회
        Member member = memberRepository.findById(orderDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member 확인 오류"));

        // 주문한 메뉴 ID를 이용하여 Menu 엔티티 조회
        Menu menu = menuRepository.findById(orderDTO.getMenuId())
                .orElseThrow(() -> new RuntimeException("Menu 확인 오류"));


        OrderedMenu orderedMenu = OrderedMenu.builder() // OrderedMenu 생성
                .menu(menu)
                .quantity(orderDTO.getQuantity()) // 주문 수량 설정
                .price(menu.getPrice()) // 메뉴 가격 설정
                .build();

        // 주문 정보를 바탕으로 UserOrder 엔티티 생성
        UserOrder order = UserOrder.builder()
                .member(member)
                .vendor(Vendor.builder().id(orderDTO.getVendor().getId()).build())
                .orderedMenus(Collections.singleton(orderedMenu)) // OrderedMenu 설정
                .build();




        // UserOrder 엔티티 저장
        userOrderRepository.save(order);

        // 생성된 주문 정보를 DTO로 변환하여 반환
        return mapEntityToDTO(order);
    }

    // 주문 ID로 주문 조회
    @Override
    public UserOrderDTO getOrderById(Long orderId) {
        UserOrder order = userOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 정보가 존재하지 않습니다."));
        return mapEntityToDTO(order);
    }


    // 장바구니 항목에서 주문 생성
    @Override
    public void createOrderFromCartItem(Member member, List<OrderedMenu> orderedMenuSet) {
        try {
            UserOrder order = UserOrder.builder()
                    .member(member)
                    .vendor(Vendor.builder().id(om.getMenu().getVendor().getId()).build()) // Vendor ID만 사용하여 생성
                    .orderedMenuList(orderedMenuSet)
                    .build();
            for(OrderedMenu om : orderedMenuSet) {
                // 주문 정보를 바탕으로 UserOrder 엔티티 생성

                // 주문한 메뉴 정보를 OrderedMenu 엔티티로 생성
                OrderedMenu orderedMenu = OrderedMenu.builder()
                        .menu(om.getMenu())
                        .quantity(om.getQuantity()) // 장바구니에 담긴 메뉴 수량 설정
                        .price(om.getMenu().getPrice())
                        .userOrder(order) // 부모 엔티티 설정
                        .build();

                // OrderedMenu를 UserOrder에 설정
                order.getOrderedMenuList().add(orderedMenu);

                // UserOrder 엔티티 저장
                userOrderRepository.save(order);
            }
        } catch (Exception e) {
            throw new RuntimeException("주문 생성 오류: " + e.getMessage());
        }
    }



    // UserOrder 엔티티를 DTO로 변환

    private UserOrderDTO mapEntityToDTO(UserOrder order) {
        List<OrderedMenuDTO> orderedMenuDTOs = new ArrayList<>(); // OrderedMenuDTO 리스트를 준비

        // UserOrder에 속한 OrderedMenu들을 순회하며 각각의 정보를 OrderedMenuDTO로 변환하여 리스트에 추가
        for (OrderedMenu orderedMenu : order.getOrderedMenus()) {
            OrderedMenuDTO orderedMenuDTO = OrderedMenuDTO.builder()
                    .menuId(orderedMenu.getMenu().getId()) // 메뉴 ID 설정
                    .quantity(orderedMenu.getQuantity()) // 주문 수량 설정
                    .price(orderedMenu.getPrice()) // 메뉴 가격 설정
                    .totalAmount(orderedMenu.getTotalAmount()) // 총 금액 설정
                    .build();

            orderedMenuDTOs.add(orderedMenuDTO); // 변환된 OrderedMenuDTO를 리스트에 추가
        }

        // UserOrder 엔티티의 정보와 변환된 OrderedMenuDTO 리스트를 사용하여 UserOrderDTO 생성
        UserOrderDTO userOrderDTO = UserOrderDTO.builder()
                .memberId(order.getMember().getId()) // 회원 ID 설정
                .orderedMenus(orderedMenuDTOs) // OrderedMenuDTO 리스트 설정
                .allTotalAmount(order.getTotalAmount()) // 총 결제 금액 설정
                .totalQuantity(order.getTotalQuantity()) // 총 주문 메뉴 수량 설정
                .vendor(VendorDTO.builder().id(order.getVendor().getId()).build()) // 가게 정보 설정
                .build();

        return userOrderDTO;
    }

}





