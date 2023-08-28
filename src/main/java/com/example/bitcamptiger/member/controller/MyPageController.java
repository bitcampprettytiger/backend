package com.example.bitcamptiger.member.controller;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.favoritePick.DTO.FavoriteVendorDTO;
import com.example.bitcamptiger.favoritePick.service.FavoriteService;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.service.MyPageService;
import com.example.bitcamptiger.order.entity.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {

    public final MyPageService myPageService;
    public final FavoriteService favoriteService;

    //내 정보 조회
    @GetMapping("/myInfo")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) throws UsernameNotFoundException{
        ResponseDTO<MemberDTO> response = new ResponseDTO<>();

        try{
            MemberDTO memberDTO = myPageService.getMyInfo(userDetails.getUsername()).get().toMemberDTO();

            response.setItem(memberDTO);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    //내 정보 수정

    //회원 탈퇴
    @DeleteMapping("/outOfMember")
    public ResponseEntity<?> outOfMember(@AuthenticationPrincipal UserDetails userDetails) {
        ResponseDTO<String> response = new ResponseDTO<>();

        try {
            String username = userDetails.getUsername();
            myPageService.outOfMember(username);

            response.setItem("회원 탈퇴가 완료되었습니다.");
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage("회원 탈퇴 중 오류가 발생했습니다.");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    //내 주문 내역
    @GetMapping("/myOrders")
    public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal UserDetails userDetails) {
        ResponseDTO<List<Orders>> response = new ResponseDTO<>();

        try {
            List<Orders> ordersList = myPageService.getMyOrders(userDetails.getUsername());

            if (ordersList.isEmpty()) {
                //주문내역 없을 경우
                response.setErrorMessage("주문 내역이 존재하지 않습니다.");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            } else {
                //주문내역 있어야 반환ok
                response.setItem(ordersList);
                response.setStatusCode(HttpStatus.OK.value());
            }

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            //조회 실패
            response.setErrorMessage("주문 내역 조회 중 오류가 발생했습니다.");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }
    //내 리뷰 내역
    @GetMapping("/myReviews")
    public ResponseEntity<?> getMyReviews(@AuthenticationPrincipal UserDetails userDetails) {
        ResponseDTO<List<Review>> response = new ResponseDTO<>();

        try {
            List<Review> reviewList = myPageService.getMyReviews(userDetails.getUsername());

            if (reviewList.isEmpty()) {
                //리뷰 내역 없을 경우
                response.setErrorMessage("리뷰 내역이 존재하지 않습니다.");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            } else {
                //리뷰 내역 있어야 반환 ok
                response.setItem(reviewList);
                response.setStatusCode(HttpStatus.OK.value());
            }

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            //조회 실패
            response.setErrorMessage("리뷰 내역 조회 중 오류가 발생했습니다.");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //내 찜 가게 내역
    @GetMapping("/myFavoriteVendors")
    public ResponseEntity<?> getMyFavoriteVendors(@AuthenticationPrincipal UserDetails userDetails) {
        ResponseDTO<List<FavoriteVendorDTO>> response = new ResponseDTO<>();

        try {
            Member member = myPageService.getMyInfo(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            List<FavoriteVendorDTO> favoriteVendors = myPageService.getMyFavoriteVendor(member.getId());

            if (favoriteVendors.isEmpty()) {
                response.setErrorMessage("찜한 내역이 없습니다.");
                response.setStatusCode(HttpStatus.OK.value());
            } else {
                System.out.println(favoriteVendors);
                response.setItem(favoriteVendors);
                response.setStatusCode(HttpStatus.OK.value());
            }

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

}