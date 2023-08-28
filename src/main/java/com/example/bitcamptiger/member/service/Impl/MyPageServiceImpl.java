package com.example.bitcamptiger.member.service.Impl;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.repository.ReviewRepository;
import com.example.bitcamptiger.favoritePick.DTO.FavoriteVendorDTO;
import com.example.bitcamptiger.favoritePick.entity.FavoriteVendor;
import com.example.bitcamptiger.favoritePick.repository.FavoriteVendorRepository;
import com.example.bitcamptiger.favoritePick.service.FavoriteService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.member.service.MyPageService;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageServiceImpl implements MyPageService {

    public final MemberRepository memberRepository;
    public final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteService favoriteService;
    private final FavoriteVendorRepository favoriteVendorRepository;


    //내 정보 조회
    @Override
    public Optional<Member> getMyInfo(String username) {

        Optional<Member> memberDTO = memberRepository.findByUsername(username);
        return memberDTO;
    }




    //내 정보 수정



    // 회원 탈퇴
    // memberRepository를 사용하여 회원을 사용자명(username)으로 찾은 다음,
    // 회원이 존재하면 해당 회원 엔터티를 삭제 -> 회원이 존재하지 않을 경우에는 예외를 발생
    @Override
    public void outOfMember(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            memberRepository.delete(member);
        } else {
            throw new RuntimeException("탈퇴할 회원을 찾을 수 없습니다.");
        }
    }

    //내 주문 내역
    @Override
    public List<Orders> getMyOrders(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return orderRepository.findByMemberId(member.getId());
        } else {
            throw new RuntimeException("주문 내역을 조회할 회원을 찾을 수 없습니다.");
        }
    }


    //내 리뷰 내역
    @Override
    public List<Review> getMyReviews(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return reviewRepository.findByMember(member);
        } else {
            throw new RuntimeException("리뷰 내역을 조회할 회원을 찾을 수 없습니다.");
        }
    }



    //내 찜 가게 내역

    @Override
    public List<FavoriteVendorDTO> getMyFavoriteVendor(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            List<FavoriteVendor> favoriteVendors = favoriteVendorRepository.findByMember(member);

            List<FavoriteVendorDTO> favoriteVendorDTOS = new ArrayList<>();
            for (FavoriteVendor favoriteVendor : favoriteVendors) {
                FavoriteVendorDTO favoriteVendorDTO = FavoriteVendorDTO.customMap(favoriteVendor);
                favoriteVendorDTOS.add(favoriteVendorDTO);
            }
            return favoriteVendorDTOS;
        } else {
            // 사용자를 찾을 수 없는 경우에 대한 처리
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }


}



