package com.example.bitcamptiger.NcloudAPI.service;

import com.example.bitcamptiger.NcloudAPI.dto.MessageDTO;
import com.example.bitcamptiger.NcloudAPI.dto.SmsRequestDTO;
import com.example.bitcamptiger.NcloudAPI.dto.SmsResponseDTO;
import com.example.bitcamptiger.NcloudAPI.dto.SmsValidationDTO;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.entity.SmsValidation;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.member.reposiitory.SmsValidationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class SMSService {

    private final MemberRepository memberRepository;

    private final SmsValidationRepository smsValidationRepository;

    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

    public SmsResponseDTO sendSms(MessageDTO messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Long time = System.currentTimeMillis();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

        List<MessageDTO> messages = new ArrayList<>();
        messages.add(messageDto);

        //6자리 랜덤값생성
        int randomvalue = (int)Math.floor((Math.random() + 1) * 100000);

        messageDto.setContent("인증번호는 [" + randomvalue + "]입니다.");

        //엔티티 하나생성 pk는 전화번호
        //컬럼은 전화번호, 인증코드
        //인증코드 db에 인서트
        SmsValidation smsValidation = SmsValidation.builder()
                .tel(messageDto.getTo())
                .code(randomvalue)
                .build();

        smsValidationRepository.save(smsValidation);

        SmsRequestDTO request = SmsRequestDTO.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content(messageDto.getContent())
                .messages(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        SmsResponseDTO response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDTO.class);

        return response;
    }

    public boolean validateMember(String name, Long tel){
        Optional<Member> member = memberRepository.findByName(name);

        if(member.isEmpty()) {
            return false;
        }

        if(member.get().getTel().equals(tel)) {
            return true;
        }

        return false;
    }


    public boolean validateCode(String tel, int code){

        Optional<SmsValidation> smsValidation = smsValidationRepository.findById(tel);
        if(smsValidation.isEmpty()){
            return false;
        }

        if(smsValidation.get().getCode() == code){
            return true;
        }

        return false;
    }



}
