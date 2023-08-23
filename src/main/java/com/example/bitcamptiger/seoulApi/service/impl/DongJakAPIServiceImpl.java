package com.example.bitcamptiger.seoulApi.service.impl;

import com.example.bitcamptiger.gyeongGiApi.entity.GyeonggiVenders;
import com.example.bitcamptiger.seoulApi.dto.DongJakAPIDTO;
import com.example.bitcamptiger.seoulApi.dto.DongJakAPIResponseDTO;
import com.example.bitcamptiger.seoulApi.entity.DongJakVenders;
import com.example.bitcamptiger.seoulApi.repository.DongJakVendersRepository;
import com.example.bitcamptiger.seoulApi.service.DongJakAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DongJakAPIServiceImpl implements DongJakAPIService {

    @Value("${dongJak.url}")
    private String apiUrl;

    @Value("${dongJak.serviceKey}")
    private String apiKey;

    @Autowired
    DongJakVendersRepository dongJakVendersRepository;
    @Override
    public List<DongJakAPIDTO> extractDongJakData() throws JsonProcessingException {
        List<DongJakAPIDTO> extractedDataList = new ArrayList<>();

        // API 호출을 위한 URL 생성
        String url = apiUrl + "?serviceKey=" + apiKey + "&page=1" + "&perPage=50";

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity 객체 생성하여 요청 데이터와 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate을 사용하여 GET 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        // ObjectMapper를 사용하여 JSON 응답을 객체로 매핑
        ObjectMapper objectMapper = new ObjectMapper();
        DongJakAPIResponseDTO responseDto = objectMapper.readValue(response.getBody(), DongJakAPIResponseDTO.class);

        // DongJakAPIDTO 리스트 추출
        List<DongJakAPIDTO> dataList = responseDto.getData();

        // 필요한 정보 추출하여 리스트에 추가
        for (DongJakAPIDTO data : dataList) {
            DongJakAPIDTO extractedData = DongJakAPIDTO.builder()
            // 여기서 필요한 정보 추출하여 객체를 생성하고 리스트에 추가
                    .연번(data.get연번())
                    .구분(data.get구분())
                    .거리가게명(data.get거리가게명())
                    .위치(data.get위치())
                    .데이터기준일자(data.get데이터기준일자())
                    .업종_판매품목(data.get업종_판매품목())
                    .build();

            extractedDataList.add(extractedData);
        }
        // 필요한 정보 추출하여 리스트에 추가
        for (DongJakAPIDTO data : dataList) {
            DongJakVenders dongJakEntity = new DongJakVenders();
            dongJakEntity.set연번(data.get연번());
            dongJakEntity.set구분(data.get구분());
            dongJakEntity.set거리가게명(data.get거리가게명());
            dongJakEntity.set업종_판매품목(data.get업종_판매품목());
            dongJakEntity.set위치(data.get위치());
            dongJakEntity.set데이터기준일자(data.get데이터기준일자());

            dongJakVendersRepository.save(dongJakEntity); // 엔티티 저장
        }


        return extractedDataList;
    }


}