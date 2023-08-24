package com.example.bitcamptiger.seoulApi.service.impl;

import com.example.bitcamptiger.seoulApi.dto.DobongguAPIDTO;
import com.example.bitcamptiger.seoulApi.dto.DobongguAPIResponseDTO;
import com.example.bitcamptiger.seoulApi.entity.DobongguVenders;
import com.example.bitcamptiger.seoulApi.repository.DobongguVendersRepository;
import com.example.bitcamptiger.seoulApi.service.DobongguAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DobongguAPIServiceImpl implements DobongguAPIService {

    @Value("${dobonggu.url}")
    private String apiUrl;

    @Value("${dobonggu.serviceKey}")
    private String apiKey;

    @Autowired
    DobongguVendersRepository dobongguVendersRepository;



    @Override
    public List<DobongguAPIDTO> extractDobongguData() throws JsonProcessingException {
        List<DobongguAPIDTO> extractedDataList = new ArrayList<>();

        // API 호출을 위한 URL 생성
        String url = apiUrl + "?serviceKey=" + apiKey + "&page=1" + "&perPage=100";

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity 객체 생성하여 요청 데이터와 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate을 사용하여 GET 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // ObjectMapper를 사용하여 JSON 응답을 객체로 매핑
        ObjectMapper objectMapper = new ObjectMapper();
        DobongguAPIResponseDTO responseDto = objectMapper.readValue(response.getBody(), DobongguAPIResponseDTO.class);

        // DobongguAPIDTO 리스트 추출
        List<DobongguAPIDTO> dataList = responseDto.getData();

        // 필요한 정보 추출하여 리스트에 추가
        for (DobongguAPIDTO data : dataList) {
            DobongguAPIDTO extractedData = DobongguAPIDTO.builder()
                    // 여기서 필요한 정보 추출하여 객체를 생성하고 리스트에 추가
                    .도로주소(data.get도로주소())
                    .연번(data.get연번())
                    .운영품목(data.get운영품목())
                    .점용목적(data.get점용목적())
                    .점용장소(data.get점용장소())
                    .지정번호(data.get지정번호())
                    .build();

            extractedDataList.add(extractedData);
        }
        // 필요한 정보 추출하여 엔티티에 저장
        for (DobongguAPIDTO data : dataList) {
            DobongguVenders existingEntity = dobongguVendersRepository.findBy연번And지정번호(data.get연번(), data.get지정번호());

            if (existingEntity == null) {
                DobongguVenders dobongguEntity = new DobongguVenders();
                dobongguEntity.set도로주소(data.get도로주소());
                dobongguEntity.set연번(data.get연번());
                dobongguEntity.set운영품목(data.get운영품목());
                dobongguEntity.set점용목적(data.get점용목적());
                dobongguEntity.set점용장소(data.get점용장소());
                dobongguEntity.set지정번호(data.get지정번호());

                dobongguVendersRepository.save(dobongguEntity); // 엔티티 저장
            }
        }

        return extractedDataList; // 필요한 정보가 담긴 DTO 리스트 반환
    }
}





