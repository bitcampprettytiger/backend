package com.example.bitcamptiger.cart.service.Impl;

import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.cart.repository.CartItemRepository;
import com.example.bitcamptiger.cart.repository.CartRepository;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.dto.MenuImageDTO;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.menu.repository.MenuImageRepository;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;
import com.example.bitcamptiger.userOrder.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final MenuImageRepository menuImageRepository;
    private final CartItemRepository cartItemRepository;
    private final UserOrderService userOrderService;


    //처음 장바구니에 상품을 담을 때는 해당 member의 장바구니를 생성해야 함.
//    public void createCart(Member member){
//        Cart cart = Cart.createCart(member);
//        cartRepository.save(cart);
//    }

    //장바구니에 menu 추가
    @Override
    public Cart addCart(Member member, Menu menu, int cartQuantity) {

        Cart cart = cartRepository.findByMemberId(member.getId());

        //cart가 비어있으면 생성.
        if(cart == null){
            cart = Cart.createCart(member);
            Cart save = cartRepository.save(cart);
            cart.setId(save.getId());
        }

        //cartItem 생성
        CartItem cartItem = cartItemRepository.findByCartIdAndMenuId(cart.getId(), menu.getId());

        //cartItem이 비어있다면 새로 생성.
        if(cartItem == null){
            cartItem = CartItem.createCartItem(cart, menu, cartQuantity);
            cartItemRepository.save(cartItem);
        }else{
            //cartItem이 비어있지 않다면 그만큼 개수를 추가
            cartItem.addCount(cartQuantity);
            cartItemRepository.save(cartItem);
        }
        return  cart;
    }




    //장바구니 조회
    @Override
    public List<CartItemDTO> getCartList(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        for(CartItem cartItem : cartItems){
            CartItemDTO cartItemDTO = CartItemDTO.of(cartItem);
            Menu menu = cartItem.getMenu();
            cartItemDTO.setMenu(menu);
            cartItemDTO.setCart(cart);

            //해당 메뉴 이미지 조회
            List<MenuImage> menuImageList = menuImageRepository.findByMenu(menu);
            List<MenuImageDTO> menuImageDTOList = new ArrayList<>();
            for(MenuImage menuImage : menuImageList){
                MenuImageDTO menuImageDTO = MenuImageDTO.of(menuImage);
                menuImageDTOList.add(menuImageDTO);
            }
            cartItemDTO.setMenuImageDTOList(menuImageDTOList);
            cartItemDTOList.add(cartItemDTO);

        }
        return cartItemDTOList;
    }


    //장바구니 수량 수정



    //장바구니 menu 삭제
    @Override
    public void deleteCartItem(Long cartId, Long menuId) {
       CartItem cartItem = cartItemRepository.findByCartItem(cartId, menuId);

       cartItemRepository.delete(cartItem);
    }


    //장바구니 menu 전체 삭제
    @Override
    public void deleteCart(CartItemDTO cartItemDTO) {
        List<CartItem> cartItemList = cartItemRepository.findByCart(cartItemDTO.getCart());

        cartItemRepository.deleteAll(cartItemList);

    }

    @Override
    public List<CartItemDTO> getCartItemsByMemberId(Long id) {
        try {
            // 멤버의 ID를 기반으로 해당 멤버의 장바구니를 조회
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("멤버 정보를 찾을 수 없습니다."));

            // 멤버의 장바구니에 담긴 아이템들을 조회
            List<CartItem> cartItems = cartItemRepository.findByCartMemberId(id);

            List<CartItemDTO> cartItemDTOList = new ArrayList<>();

            // 조회된 각 장바구니 아이템을 DTO로 변환하여 리스트에 추가
            for (CartItem cartItem : cartItems) {
                CartItemDTO cartItemDTO = CartItemDTO.of(cartItem);
                cartItemDTOList.add(cartItemDTO);
            }

            return cartItemDTOList;
        } catch (Exception e) {
            throw new RuntimeException("장바구니 아이템 조회 오류: " + e.getMessage());
        }
    }



}
