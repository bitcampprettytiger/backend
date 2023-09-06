package com.example.bitcamptiger.NcloudAPI.controller;

import com.example.bitcamptiger.NcloudAPI.dto.MessageDTO;
import com.example.bitcamptiger.NcloudAPI.dto.SmsResponseDTO;
import com.example.bitcamptiger.NcloudAPI.service.SMSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
public class SmsController {
    private final SMSService smsService;

    @GetMapping("/send")
    public String getSmsPage() {
        return "sendSms";
    }

    @PostMapping("/sms/send")
    public String sendSms(MessageDTO messageDto, Model model) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        SmsResponseDTO response = smsService.sendSms(messageDto);
        model.addAttribute("response", response);
        return "result";
    }
}
