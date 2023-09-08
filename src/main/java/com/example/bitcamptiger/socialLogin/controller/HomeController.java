package com.example.bitcamptiger.socialLogin.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.jwt.JwtTokenProvider;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.socialLogin.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
public class HomeController {

    //    private final AppleService appleService;
    private final KakaoService kakaoService;

    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping(value = "/api/kakao", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

        return "SocialLogin";
    }

    @RequestMapping(value = "/API/kakao/login",  method = RequestMethod.POST)
    public ResponseEntity<?> KakaoLogin(
            @RequestBody MemberDTO memberDTO){
        ResponseDTO<MemberDTO> response = new ResponseDTO<>();

        try {
            Member member = kakaoService.getKakaoMember(memberDTO);

            MemberDTO kakaoMemberDTO = member.toMemberDTO();

            String token = jwtTokenProvider.create(member);
            String refreshToken = jwtTokenProvider.accessToken(member);

            kakaoMemberDTO.setAccessToken(token);
            kakaoMemberDTO.setRefreshToken(refreshToken);

            response.setItem(kakaoMemberDTO);
            response.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



}
