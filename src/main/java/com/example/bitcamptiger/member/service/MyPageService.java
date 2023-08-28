package com.example.bitcamptiger.member.service;

import com.example.bitcamptiger.member.entity.Member;

import java.util.Optional;

public interface MyPageService {

    //내 정보 조회
    Optional<Member> getMyInfo(String username);


    //내 정보 수정

    //회원 탈퇴

    //내 주문 내역

    //내 리뷰 내역

    //내 찜 가게 내역

}
