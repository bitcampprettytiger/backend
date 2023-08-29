package com.example.bitcamptiger.seoulApi.service.impl;

import com.example.bitcamptiger.seoulApi.dto.DongdaemunAPIDTO;
import com.example.bitcamptiger.seoulApi.dto.DongdaemunAPIResponseDTO;
import com.example.bitcamptiger.seoulApi.entity.DongdaemunVenders;
import com.example.bitcamptiger.seoulApi.repository.DongdaemunVendersRepository;
import com.example.bitcamptiger.seoulApi.service.DongdaemunAPIService;
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
public class DongdaemunAPIServiceImpl implements DongdaemunAPIService {

    @Value("${dongdaemun.url}")
    private String apiUrl;

    @Value("${dongdaemun.serviceKey}")
    private String apiKey;

    @Autowired
    DongdaemunVendersRepository dongdaemunVendersRepository;


    @Override
    public List<DongdaemunAPIDTO> extractDongdaemunData() throws JsonProcessingException {
        List<DongdaemunAPIDTO> extractedDataList = new ArrayList<>();

        // API 호출을 위한 URL 생성
        String url = apiUrl + "?serviceKey=" + apiKey + "&page=1" + "&perPage=30";

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity 객체 생성하여 요청 데이터와 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate을 사용하여 GET 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class
        );

        // ObjectMapper를 사용하여 JSON 응답을 객체로 매핑
        ObjectMapper objectMapper = new ObjectMapper();
        DongdaemunAPIResponseDTO responseDto = objectMapper.readValue(response.getBody(), DongdaemunAPIResponseDTO.class);

        // DongdaemunAPIDTO 리스트 추출
        List<DongdaemunAPIDTO> dataList = responseDto.getData();

        // 필요한 정보 추출하여 리스트에 추가
        for (DongdaemunAPIDTO data : dataList) {
            DongdaemunAPIDTO extractedData = DongdaemunAPIDTO.builder()
                    // 여기서 필요한 정보 추출하여 객체를 생성하고 리스트에 추가
                    .연번(data.get연번())
                    .거리가게명(data.get거리가게명())
                    .업종(data.get업종())
                    .주소(data.get주소())
                    .데이터수집일자(data.get데이터수집일자())
                    .build();

            extractedDataList.add(extractedData);
        }
        // 필요한 정보 추출하여 엔티티에 저장
        for (DongdaemunAPIDTO data : dataList) {
            DongdaemunVenders existingEntity = dongdaemunVendersRepository.findBy연번(data.get연번());

            if (existingEntity == null) {
                DongdaemunVenders dongdaemunEntity = new DongdaemunVenders();
                dongdaemunEntity.set연번(data.get연번());
                dongdaemunEntity.set거리가게명(data.get거리가게명());
                dongdaemunEntity.set업종(data.get업종());
                dongdaemunEntity.set주소(data.get주소());
                dongdaemunEntity.set데이터수집일자(data.get데이터수집일자());

                dongdaemunVendersRepository.save(dongdaemunEntity); // 엔티티 저장
            }
        }

        return extractedDataList; // 필요한 정보가 담긴 DTO 리스트 반환
    }
}
