package com.example.bitcamptiger.cart.service.Impl;

import com.example.bitcamptiger.cart.dto.CartDTO;
import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.cart.repository.CartItemRepository;
import com.example.bitcamptiger.cart.repository.CartRepository;
import com.example.bitcamptiger.cart.service.CartService;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.menu.repository.MenuImageRepository;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final MenuImageRepository menuImageRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartDTO> getCartList(Long memberId) {
        //1. member id 찾아오기

        List<Cart> cartList = cartRepository.findByMemberId(memberId);

        List<CartDTO> cartDTOList = new ArrayList<>();


        return null;
    }

    @Override
    public void insertCart(CartItemDTO cartItemDTO) {

//        Member member = memberRepository.findById(customUserDetails.getUser().getId()).orElseThrow();
        Member member = memberRepository.findById(cartItemDTO.getCart().getMember().getId()).orElseThrow();
        Menu menu = menuRepository.findById(cartItemDTO.getMenu().getId()).orElseThrow();

        CartItem existingCartItem = cartItemRepository.findByMenuAndCart_Member(menu, member);
            if(existingCartItem == null){
                CartItem cartItem = cartItemDTO.createCartItem();
                cartItemRepository.save(cartItem);
            }else{
                //장바구니에 메뉴가 이미 담겨있을 경우 수량 더하기
                existingCartItem.setCartQuantity(existingCartItem.getCartQuantity() + cartItemDTO.getCartQuantity());
                cartItemRepository.save(existingCartItem);
            }

        }



}
