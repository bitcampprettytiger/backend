package com.example.bitcamptiger.member.service;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.favoritePick.DTO.FavoriteVendorDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.order.entity.Orders;

import java.util.List;
import java.util.Optional;

public interface MyPageService {

    //내 정보 조회
    Optional<Member> getMyInfo(String username);

    // 회원 탈퇴
    void outOfMember(String username);

    //내 주문 내역
    List<Orders> getMyOrders(String username);

    //내 리뷰 내역
    List<Review> getMyReviews(String username);

    //내 찜 가게 내역
    List<FavoriteVendorDTO> getMyFavoriteVendor(Long memberId);


    //내 찜 가게 내역



    //내 정보 수정




}
