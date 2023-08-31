package com.example.bitcamptiger.member.controller;

import com.example.bitcamptiger.Review.dto.MyReviewResponse;
import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.favoritePick.DTO.FavoriteVendorDTO;
import com.example.bitcamptiger.favoritePick.service.FavoriteService;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.dto.MyInfoDTO;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.service.MyPageService;
import com.example.bitcamptiger.order.dto.OrderDTO;
import com.example.bitcamptiger.order.dto.OrderMenuDTO;
import com.example.bitcamptiger.order.entity.OrderMenu;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.payments.dto.PaymentDTO;
import com.example.bitcamptiger.payments.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {

    public final MyPageService myPageService;
    public final FavoriteService favoriteService;
    private final PasswordEncoder passwordEncoder;
    private final PaymentService paymentService;

    //내 정보 조회
    @Operation(summary = "myInfo", description = "회원 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
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
    @Operation(summary = "changeInfo", description = "회원 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PutMapping("/member/update")
    public ResponseEntity<?> updateMemberInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid MyInfoDTO updatedInfo,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        ResponseDTO<MyInfoDTO> responseDTO = new ResponseDTO<>();
        try {
            // 현재 로그인한 사용자의 정보를 가져옴
            Member member = customUserDetails.getUser();

            // 닉네임 및 비밀번호 수정 여부 확인
            boolean updatedNickname = false;
            boolean updatedPassword = false;

            if (updatedInfo.getNickName() != null) {
                member.setNickname(updatedInfo.getNickName());
                updatedNickname = true;
            }

            if (updatedInfo.getPassword() != null && !updatedInfo.getPassword().isEmpty()) {
                member.setPassword(passwordEncoder.encode(updatedInfo.getPassword()));
                updatedPassword = true;
            }

            // 회원 정보 업데이트
            MemberDTO updatedMemberDTO = myPageService.updateMemberInfo(
                    member, updatedInfo.getNickName(), updatedInfo.getPassword()
            );

            // 응답 메시지 생성
            String message = "";
            if (updatedNickname && updatedPassword) {
                message = "닉네임, 비밀번호 수정이 완료되었습니다.";
            } else if (updatedNickname) {
                message = "닉네임이 수정되었습니다.";
            } else if (updatedPassword) {
                message = "비밀번호가 수정되었습니다.";
            }

            // 응답 DTO 구성
            MyInfoDTO updatedDTO = MyInfoDTO.builder()
                    .password(updatedMemberDTO.getPassword())
                    .username(updatedMemberDTO.getUsername())
                    .role(updatedMemberDTO.getRole())
                    .build();

            responseDTO.setItem(updatedDTO);
            responseDTO.setMessage(message);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception exception) {
            responseDTO.setErrorMessage(exception.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }



    //회원 탈퇴
    @Operation(summary = "outOfMember", description = "회원 탈퇴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
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
    @Operation(summary = "myOrders", description = "회원 주문 내역 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/myOrders")
    public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<List<OrderDTO>> response = new ResponseDTO<>();
        try {
            Member member = customUserDetails.getUser();
            List<OrderDTO> orderDTOList = myPageService.getMyOrderDTOs(member.getUsername());

            if (orderDTOList.isEmpty()) {
                // 주문 내역이 없는 경우
                response.setErrorMessage("주문 내역이 없습니다.");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            } else {
                int totalOrderCount = orderDTOList.size();
                // 각 주문 내역 DTO에 전체 주문 내역 개수를 설정
                for (OrderDTO orderDTO : orderDTOList) {
                    orderDTO.setOrderCount(totalOrderCount);
                }

                response.setItem(orderDTOList);
                response.setMessage("총 " + totalOrderCount + "개의 주문 내역이 있습니다.");
                response.setStatusCode(HttpStatus.OK.value());
            }

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    // 내 결제 내역을 조회하는 엔드포인트
    @Operation(summary = "myPaymentList", description = "회원 결제내역 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/myPaymentList")
    public ResponseEntity<?> getMyPaymentList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<List<PaymentDTO>> response = new ResponseDTO<>();
        try {
            // 로그인한 사용자의 정보를 가져옴
            Member member = customUserDetails.getUser();

            // 사용자의 결제 내역을 조회하고 DTO로 변환
            List<PaymentDTO> paymentDTOList = paymentService.getPaymentList(member);

            if (paymentDTOList.isEmpty()) {
                // 결제 내역이 없는 경우
                response.setErrorMessage("결제 내역이 없습니다.");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            } else {
                // 결제 내역이 있는 경우
                int totalPaymentCount = paymentDTOList.size();

                // 결제 내역 개수를 각 결제 내역 DTO에 추가
                for (PaymentDTO paymentDTO : paymentDTOList) {
                    paymentDTO.setPaymentCount(totalPaymentCount);
                }

                // 결제 내역 개수를 응답 DTO에 추가
                response.setItem(paymentDTOList);
                response.setMessage("총 " + totalPaymentCount + "개의 결제 내역이 있습니다.");
                response.setStatusCode(HttpStatus.OK.value());
            }

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @Operation(summary = "myReviews", description = "회원 리뷰내역 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/myReviews")
    public ResponseEntity<ResponseDTO<MyReviewResponse>> getMyReviews(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<MyReviewResponse> response = new ResponseDTO<>();

        try {
            List<ReviewDto> reviewList = myPageService.getMyReviewDTOs(customUserDetails.getUsername());
            int numberOfReviews = reviewList.size();

            if (reviewList.isEmpty()) {
                // 리뷰 내역 없을 경우
                response.setErrorMessage("리뷰 내역이 존재하지 않습니다.");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            } else {
                MyReviewResponse myReviewResponse = new MyReviewResponse(reviewList, numberOfReviews);
                response.setItem(myReviewResponse);
                response.setStatusCode(HttpStatus.OK.value());
            }

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // 조회 실패
            response.setErrorMessage("리뷰 내역 조회 중 오류가 발생했습니다.");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }
    //내 찜 가게 내역
    @Operation(summary = "myFavoriteVendors", description = "회원 찜내역 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/myFavoriteVendors")
    public ResponseEntity<?> getMyFavoriteVendors(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 응답을 담을 DTO 생성
        ResponseDTO<List<FavoriteVendorDTO>> response = new ResponseDTO<>();

        try {
            // 로그인한 사용자의 찜한 가게 내역을 조회하고 DTO로 변환
            List<FavoriteVendorDTO> favoriteVendorDTOList = myPageService.getMyFavoriteVendorDTOs(customUserDetails.getUsername());

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