//package com.example.bitcamptiger.cart.service.Impl;
//
//import com.example.bitcamptiger.cart.dto.CartDTO;
//import com.example.bitcamptiger.cart.entity.Cart;
//import com.example.bitcamptiger.cart.repository.CartRepository;
//import com.example.bitcamptiger.cart.service.CartService;
//import com.example.bitcamptiger.member.entity.Member;
//import com.example.bitcamptiger.member.reposiitory.MemberRepository;
//import com.example.bitcamptiger.menu.entity.Menu;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class CartServiceImpl implements CartService {
//
//    private final MemberRepository memberRepository;
//    private final CartRepository cartRepository;
//
//    @Override
//    public List<CartDTO> getCartList(Long memberId) {
//        //1. member id 찾아오기
//
//        List<Cart> cartList = cartRepository.findByMember(memberId);
//
//        List<CartDTO> cartDTOList = new ArrayList<>();
//
//
//        return null;
//    }
//
//    @Override
//    public void insertCart(CartDTO cartDTO) {
//
////        Member member = memberRepository.findById(userDetails.getUser().getId()).get();
////
////        Menu menu = menuRepository.findById(menuId).get();
//
//    }
//
//
//}
