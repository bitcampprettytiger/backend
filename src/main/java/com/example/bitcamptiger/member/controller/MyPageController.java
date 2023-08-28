package com.example.bitcamptiger.member.controller;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.favoritePick.DTO.FavoriteVendorDTO;
import com.example.bitcamptiger.favoritePick.service.FavoriteService;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.service.MyPageService;
import com.example.bitcamptiger.order.dto.OrderDTO;
import com.example.bitcamptiger.order.dto.OrderMenuDTO;
import com.example.bitcamptiger.order.entity.OrderMenu;
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

import java.util.ArrayList;
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


    // 내 주문 내역을 조회하는 엔드포인트
    @GetMapping("/myOrders")
    public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal UserDetails userDetails) {
        // 응답을 담을 DTO 생성
        ResponseDTO<List<OrderDTO>> response = new ResponseDTO<>();

        try {
            // 로그인한 사용자의 주문 내역을 조회하고 DTO로 변환
            List<OrderDTO> orderDTOList = myPageService.getMyOrderDTOs(userDetails.getUsername());

            // 응답 데이터를 설정하고 OK 상태로 반환
            response.setItem(orderDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // 오류 발생 시 에러 메시지를 설정하고 BAD_REQUEST 상태로 반환
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
        // 응답을 담을 DTO 생성
        ResponseDTO<List<FavoriteVendorDTO>> response = new ResponseDTO<>();

        try {
            // 로그인한 사용자의 찜한 가게 내역을 조회하고 DTO로 변환
            List<FavoriteVendorDTO> favoriteVendorDTOList = myPageService.getMyFavoriteVendorDTOs(userDetails.getUsername());

            if (favoriteVendorDTOList.isEmpty()) {
                // 찜한 가게 내역이 없는 경우에 대한 처리
                response.setErrorMessage("찜한 가게가 없습니다.");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            } else {
                // 찜한 가게 내역이 있는 경우에 대한 처리
                System.out.println(favoriteVendorDTOList);
                response.setItem(favoriteVendorDTOList);
                response.setStatusCode(HttpStatus.OK.value());
            }

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // 오류 발생 시 에러 메시지를 설정하고 BAD_REQUEST 상태로 반환
            response.setErrorMessage("찜한 가게 내역 조회 중 오류가 발생했습니다.");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

    }
}