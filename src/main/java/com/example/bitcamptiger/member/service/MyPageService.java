package com.example.bitcamptiger.member.service;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.favoritePick.DTO.FavoriteVendorDTO;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.order.dto.OrderDTO;
import com.example.bitcamptiger.order.entity.Orders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface MyPageService {

    //내 정보 조회
    Optional<Member> getMyInfo(String username);

    //내 정보 수정
//    Member updateMemberInfo(Member member, String nickname, String password);

    //내 정보 수정
//    @Override
//    public Member updateMemberInfo(Member member, String nickname, String password) {
//        // 수정할 정보 업데이트
//        member.setNickname(nickname);
//
//        // 비밀번호가 제공된 경우에만 비밀번호 업데이트
//        if (password != null && !password.isEmpty()) {
//            member.setPassword(passwordEncoder.encode(password));
//        }
//
//        // 회원 정보 업데이트
//        return memberRepository.save(member);
//    }

    //내 정보 수정
//    @Override
//    public Member updateMemberInfo(Member member, String nickname, String password) {
//        // 수정할 정보 업데이트
//        member.setNickname(nickname);
//
//        // 비밀번호가 제공된 경우에만 비밀번호 업데이트
//        if (password != null && !password.isEmpty()) {
//            member.setPassword(passwordEncoder.encode(password));
//        }
//
//        // 회원 정보 업데이트
//        return memberRepository.save(member);
//    }


    //내 정보 수정
//    @Override
//    public Member updateMemberInfo(Member member, String nickname, String password) {
//        // 수정할 정보 업데이트
//        member.setNickname(nickname);
//
//        // 비밀번호가 제공된 경우에만 비밀번호 업데이트
//        if (password != null && !password.isEmpty()) {
//            member.setPassword(passwordEncoder.encode(password));
//        }
//
//        // 회원 정보 업데이트
//        return memberRepository.save(member);
//    }
    MemberDTO updateMemberInfo(Member member, String updatedNickName, String newPassword);

    // 회원 탈퇴
    void outOfMember(String username);


    //내 주문 내역
    // 내 주문 내역을 조회하고 DTO로 변환하여 반환
    List<OrderDTO> getMyOrderDTOs(String username);

    //내 리뷰 내역
    List<Review> getMyReviews(String username);

    // 내 찜 가게 내역을 조회하고 DTO로 변환하여 반환
    List<FavoriteVendorDTO> getMyFavoriteVendorDTOs(String username);



    //내 정보 수정




}
