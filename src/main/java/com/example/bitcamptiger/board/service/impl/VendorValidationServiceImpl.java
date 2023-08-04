package com.example.bitcamptiger.board.service.impl;


import com.example.bitcamptiger.board.dto.ValidationResponseDto;
import com.example.bitcamptiger.board.dto.VendorValidationDto;
import com.example.bitcamptiger.board.service.VendorValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class VendorValidationServiceImpl implements VendorValidationService {
    @Value("${vendor.serviceKey}")
    private String key;

    @Value("${vendor.validateUrl}")
    private String validateUrl;

    @Override
    public ValidationResponseDto isValidBusinessInfo(VendorValidationDto vendorValidationDto) throws JsonProcessingException, JSONException {
        // API 호출을 위한 URL 생성
        String url = validateUrl +"?serviceKey=" + key;

        // ObjectMapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(" \"businesses\": [");
        sb.append("     {");
        sb.append("         \"b_no\": \"" + vendorValidationDto.getB_no() + "\",");
        sb.append("         \"start_dt\": \"" + vendorValidationDto.getStart_dt() + "\",");
        sb.append("         \"p_nm\": \"" + vendorValidationDto.getP_nm() + "\"");
        sb.append("     }");
        sb.append(" ]");
        sb.append("}");

        String jsonString = sb.toString();

        // Map을 JSON 형식의 문자열로 변환
//        String requestBody = objectMapper.writeValueAsString(jsonObject);

        // HttpHeaders 객체 생성 및 Content-Type 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity 객체 생성하여 요청 데이터와 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        // RestTemplate을 사용하여 POST 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        System.out.println(response.getBody());

        // API 응답으로 받은 VendorValidationDto
        //ValidationResponseDto responseDto = response.getBody();

        // API 호출 결과에 대한 유효성 검사
//        if (responseDto != null && responseDto.getData()!= null) {
//            for (VendorValidationDto dataDto : responseDto.getData()) {
//                // 원하는 정보 추출 및 작업 수행
//                System.out.println("사업자번호: " + dataDto.getB_no());
//                System.out.println("개업일자: " + dataDto.getStart_dt());
//                System.out.println("대표자명: " + dataDto.getP_nm());
//                System.out.println("대표자명2: " + dataDto.getP_nm2());
//                System.out.println("업체명: " + dataDto.getB_nm());
//                System.out.println("법인번호: " + dataDto.getCorp_no());
//                System.out.println("주업태명: " + dataDto.getB_sector());
//                System.out.println("주종목명: " + dataDto.getB_type());
//
//                // 기타 원하는 작업 수행
//            }
//        } else {
//            System.out.println("데이터가 확인되지 않습니다. 올바른 사업자등록번호를 입력해주세요.");
//        }

        // API 응답으로 받은 VendorValidationDto 반환
        return null;

    }
}
