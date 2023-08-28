package com.example.bitcamptiger.member.service.Impl;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.member.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageServiceImpl implements MyPageService {

    public final MemberRepository memberRepository;

    //내 정보 조회
    @Override
    public Optional<Member> getMyInfo(String username) {

        Optional<Member> memberDTO = memberRepository.findByUsername(username);
        return memberDTO;
    }




    //내 정보 수정

    //회원 탈퇴

    //내 주문 내역

    //내 리뷰 내역

    //내 찜 가게 내역
}



