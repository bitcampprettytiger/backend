package com.example.bitcamptiger.cart.service.Impl;

import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.cart.repository.CartItemRepository;
import com.example.bitcamptiger.cart.repository.CartRepository;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.dto.MenuImageDTO;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.menu.repository.MenuImageRepository;
import com.example.bitcamptiger.menu.repository.MenuRepository;
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



    //처음 장바구니에 상품을 담을 때는 해당 member의 장바구니를 생성해야 함.
    public void createCart(Member member){
        Cart cart = Cart.createCart(member);
        cartRepository.save(cart);
    }



    //장바구니에 menu 추가
    @Override
    public void addCart(Member member, Menu menu, int cartQuantity) {

       Cart cart = cartRepository.findByMemberId(member.getId());

       //cart가 비어있으면 생성.
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
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

    }

    //장바구니 조회
    @Override
    public List<CartItemDTO> getCartList(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        for(CartItem cartItem : cartItems){
            CartItemDTO cartItemDTO = CartItemDTO.of(cartItem);
            Menu menu = cartItem.getMenu();

            MenuDTO menuDTO = MenuDTO.of(menu);

            List<MenuImage> menuImageList = menuImageRepository.findByMenu(menu);
            List<MenuImageDTO> menuImageDTOList = new ArrayList<>();
            for(MenuImage menuImage : menuImageList){
                MenuImageDTO menuImageDTO = MenuImageDTO.of(menuImage);
                menuImageDTOList.add(menuImageDTO);
            }

            menuDTO.setMenuImageList(menuImageDTOList);
            cartItemDTO.setMenu(menu);

            cartItemDTOList.add(cartItemDTO);
        }
        return cartItemDTOList;
    }

    //장바구니 menu 삭제
    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    //장바구니 menu 전체 삭제
    @Override
    public void deleteCart(Long memberId) {
        cartItemRepository.deleteByCartMemberId(memberId);

    }




//    @Override
//    public void insertCart(CartItemDTO cartItemDTO) {
//
////        Member member = memberRepository.findById(customUserDetails.getUser().getId()).orElseThrow();
//        Member member = memberRepository.findById(cartItemDTO.getCart().getMember().getId()).orElseThrow();
//        Menu menu = menuRepository.findById(cartItemDTO.getMenu().getId()).orElseThrow();
//
//        CartItem existingCartItem = cartItemRepository.findByMenuAndCart_Member(menu, member);
//            if(existingCartItem == null){
//                CartItem cartItem = cartItemDTO.createCartItem();
//                cartItemRepository.save(cartItem);
//            }else{
//                //장바구니에 메뉴가 이미 담겨있을 경우 수량 더하기
//                existingCartItem.setCartQuantity(existingCartItem.getCartQuantity() + cartItemDTO.getCartQuantity());
//                cartItemRepository.save(existingCartItem);
//            }
//
//        }



}
