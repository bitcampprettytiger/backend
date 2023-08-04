package com.example.bitcamptiger.vendor.service.Impl;


import com.example.bitcamptiger.vendor.dto.BusinessDataDto;
import com.example.bitcamptiger.vendor.dto.BusinessResponseDto;
import com.example.bitcamptiger.vendor.service.VendorAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VendorAPIServiceImpl implements VendorAPIService {

    // application.properties에서 vendor.serviceKey 값을 주입받음
    @Value("${vendor.serviceKey}")
    private String key;

    @Value("${vendor.statusUrl}")
    private String statusUrl;

    @Override
    public BusinessResponseDto checkBusiness(String businessNumber) throws JsonProcessingException {
        // API 호출을 위한 URL 생성
//        String url = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + key;
        String url = statusUrl + "?serviceKey=" + key;
        System.out.println("=========================url======" + url);
        // ObjectMapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("=========================url======" + businessNumber);
        // 보낼 데이터를 Map 형식으로 생성
        Map<String, List<String>> data = new HashMap<>();
        data.put("b_no", Collections.singletonList(businessNumber));

        // Map을 JSON 형식의 문자열로 변환
        String requestBody = objectMapper.writeValueAsString(data);

        // HttpHeaders 객체 생성 및 Content-Type 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity 객체 생성하여 요청 데이터와 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // RestTemplate을 사용하여 POST 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BusinessResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, BusinessResponseDto.class);

        // API 응답으로 받은 BusinessResponseDto
        BusinessResponseDto responseDto = response.getBody();

        // API 호출 결과에 대한 유효성 검사
        if (responseDto != null && responseDto.getData() != null) {
            for (BusinessDataDto dataDto : responseDto.getData()) {
                System.out.println("사업자번호: " + dataDto.getB_no());
                System.out.println("납세자상태: " + dataDto.getB_stt());
                System.out.println("납세자상태코드: " + dataDto.getB_stt_cd());
                System.out.println("과세유형: " + dataDto.getTax_type());
                System.out.println("과세코드: " + dataDto.getTax_type_cd());
                System.out.println("폐업일: " + dataDto.getEnd_dt());
                System.out.println("단위과세전환폐업여부: " + dataDto.getUtcc_yn());
                System.out.println("과세유형전환일자: " + dataDto.getTax_type_change_dt());
                System.out.println("세금계산서적용일자: " + dataDto.getInvoice_apply_dt());
                System.out.println("직전과세유형: " + dataDto.getRbf_tax_type());
                System.out.println("직전과세유형코드: " + dataDto.getRbf_tax_type_cd());

                //추가적으로 "계속 사업자" 라면 추가메세지 출력
                if ("계속사업자".equals(dataDto.getB_stt())) {
                    System.out.println("해당 사업자번호는 유효한 번호 입니다.");
                } else if ("휴업자".equals(dataDto.getB_stt())) {
                    System.out.println("해당 등록번호는 '휴업자' 상태입니다. 휴업자인 경우 등록하실 수 없습니다.");
                } else if ("폐업자".equals(dataDto.getB_stt())) {
                    System.out.println("해당 등록번호는 '폐업자' 상태입니다. 폐업자인 경우 등록하실 수 없습니다.");
                }
            }
        } else {
            System.out.println("데이터가 확인되지 않습니다. 올바른 사업자등록번호를 입력해주세요.");
        }

        // API 응답으로 받은 BusinessResponseDto 반환
        return responseDto;
    }
}