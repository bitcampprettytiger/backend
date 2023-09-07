package com.example.bitcamptiger.NcloudAPI.controller;

import com.example.bitcamptiger.NcloudAPI.dto.MessageDTO;
import com.example.bitcamptiger.NcloudAPI.dto.SmsValidationDTO;
import com.example.bitcamptiger.NcloudAPI.dto.SmsResponseDTO;
import com.example.bitcamptiger.NcloudAPI.dto.UpdatePasswordDTO;
import com.example.bitcamptiger.NcloudAPI.service.SMSService;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/API")
@RequiredArgsConstructor
public class SmsController {
    private final SMSService smsService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    
    
    @GetMapping("/send")
    public String getSmsPage() {
        return "sendSms";
    }

//    @PostMapping("/sms/send")
//    public String sendSms(MessageDTO messageDto, Model model) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
//        SmsResponseDTO response = smsService.sendSms(messageDto);
//        model.addAttribute("response", response);
//        return "result";
//    }


    //문자메시지 보내기
    @PostMapping("/sms/send")
    public ResponseEntity<?> sendSms(@RequestBody MessageDTO messageDto)
            throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        ResponseDTO<SmsResponseDTO> response = new ResponseDTO<>();
        System.out.println(messageDto.getTo());
        try {
            SmsResponseDTO smsResponseDTO = smsService.sendSms(messageDto);
            System.out.println(smsResponseDTO);
            response.setItem(smsResponseDTO);
            response.setStatusCode(HttpStatus.OK.value());
            return  ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    
    //검증
    @PostMapping("/validateMember")
    public ResponseEntity<?> validateMember(@RequestBody MemberDTO memberDTO){
        ResponseDTO<MemberDTO> response = new ResponseDTO<>();

        try{
            boolean isMemberValid = smsService.validateMember(memberDTO.getName(), memberDTO.getTel());
            System.out.println(isMemberValid);
            if(isMemberValid){
                response.setItem(memberDTO);
                response.setMessage("입력한 이름과 전화번호가 일치합니다.");
                response.setStatusCode(HttpStatus.OK.value());
            }else{
                response.setErrorMessage("입력한 이름과 전화번호가 일치하지 않습니다.");
            }
            System.out.println(response.getStatusCode());
            return ResponseEntity.ok(response);

        } catch  (Exception e) {
            System.out.println(e.getMessage());
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }


    }


    @PostMapping("/sms/validateCode")
    public ResponseEntity<?> validateCode(@RequestBody SmsValidationDTO smsValidationDTO) {
        //서비스에 메소드 만들어서 호출
        //db
        ResponseDTO<SmsValidationDTO> response = new ResponseDTO<>();

        try{

            boolean isSmsValid = smsService.validateCode(smsValidationDTO.getTel(), smsValidationDTO.getCode());
            System.out.println(isSmsValid);

            if(isSmsValid){
                response.setMessage("인증이 완료되었습니다.");
                response.setStatusCode(HttpStatus.OK.value());
            }else{
                response.setErrorMessage("인증에 실패하였습니다.");
            }
            System.out.println(response.getMessage());
            return ResponseEntity.ok(response);

        }catch  (Exception e) {
            System.out.println(e.getMessage());
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

    }








}
