package com.example.bitcamptiger.member.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.jwt.JwtTokenProvider;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/member")
public class MemberController {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;



    @Operation(summary = "Add member",description = "멤버 회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "통과"),
            @ApiResponse(responseCode = "400",description = "실패")
    })
    @PostMapping("/member/join")
    public ResponseEntity<?> join(
//    RequestBody 안에있는 Member을 가져온다.
            @RequestBody @Valid MemberDTO member , BindingResult bindingResult
            ){


        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        System.out.println(member);
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();
        try {
//            비밀번호 암호화
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            System.out.println(member.getPassword());
//            회원가입(MemberEntity 리턴하도록)
           MemberDTO returnMember = memberService.join(member);
            System.out.println(member);
//            DTO로 변환(비밀번호는 "")
//            MemberDTO memberDTO =  member.toMemberDTO();
//            System.out.println(memberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
//            ResponsEntity에 DTO 담아서 전송
            return  ResponseEntity.ok().body(returnMember);
        }
        catch (Exception exception){
            responseDTO.setErrorMessage(exception.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }



    @Operation(summary = "Add member",description = "멤버 로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "통과"),
            @ApiResponse(responseCode = "400",description = "실패")
    })
    @PostMapping("/member/login")
    public ResponseEntity<?> login( @RequestBody @Valid MemberDTO member){
        ResponseDTO<MemberDTO> response = new ResponseDTO<>();
        Member loginmember =  member.toMemberEntity();
        try {

//            로그인 처리
//            로그인 시 로그인 한 Member 엔티티 리턴
            Optional<Member> loginUser = memberService.login(loginmember);
            System.out.println("controller"+loginUser);
//            위에서 받아온 엔티티가  null이 아닐때
            if(!loginUser.isEmpty()){
                System.out.println("!loginUser.isEmpty()");
//                로그인한 유저에 대한 토큰 발행
                String token = jwtTokenProvider.create(loginUser.get());
                System.out.println("token"+token);
//              DTO로 변환
                MemberDTO memberDTO = loginUser.get().toMemberDTO();
//               발행된 토큰 DTO에 담기
                memberDTO.setToken(token);
//                ResponseDTO에 MemberDTO 담아서 ResponseEntity의 body로 리턴
                response.setItem(memberDTO);
                response.setStatusCode(HttpStatus.OK.value());
                System.out.println(response);
//                response.setStatusCode(HttpStatus);
                return ResponseEntity.ok().body(response);
            } else {
                response.setErrorMessage("login failed");
                return ResponseEntity.badRequest().body(response);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

    }
}
