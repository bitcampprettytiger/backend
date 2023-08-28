package com.example.bitcamptiger.member.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {

    public final MyPageService myPageService;

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

    //내 주문 내역

    //내 리뷰 내역

    //내 찜 가게 내역
}