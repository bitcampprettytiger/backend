package com.example.bitcamptiger.seoulApi.service.impl;

import com.example.bitcamptiger.seoulApi.dto.DongdaemunAPIDTO;
import com.example.bitcamptiger.seoulApi.dto.GangNamAPIDTO;
import com.example.bitcamptiger.seoulApi.dto.GangNamAPIResponseDTO;
import com.example.bitcamptiger.seoulApi.entity.GangNamVenders;
import com.example.bitcamptiger.seoulApi.repository.GangNamVendersRepository;
import com.example.bitcamptiger.seoulApi.service.GangNamAPIService;
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
public class GangNamAPIServiceImpl implements GangNamAPIService {

    @Value("${gangNam.url}")
    private String apiUrl;

    @Value("${gangNam.serviceKey}")
    private String apiKey;


    @Autowired
    GangNamVendersRepository gangNamVendersRepository;

    @Override
    public List<GangNamAPIDTO> extractGangNamData() throws JsonProcessingException {
        List<GangNamAPIDTO> extractedDataList = new ArrayList<>();

        // API 호출을 위한 URL 생성
        String url = apiUrl + "?serviceKey=" + apiKey + "&page=1" + "&perPage=200";

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
        GangNamAPIResponseDTO responseDto = objectMapper.readValue(response.getBody(), GangNamAPIResponseDTO.class);

        // GangNamAPIDTO 리스트 추출
        List<GangNamAPIDTO> dataList = responseDto.getData();

        // DTO 리스트에 추가
        for (GangNamAPIDTO data : dataList) {
            GangNamAPIDTO extractedData = GangNamAPIDTO.builder()
                    .관리부서전화번호(data.get관리부서전화번호())
                    .관리부서명(data.get관리부서명())
                    .구분(data.get구분())
                    .데이터기준일자(data.get데이터기준일자())
                    .소재지도로명주소(data.get소재지도로명주소())
                    .소재지지번주소(data.get소재지지번주소())
                    .취급물품(data.get취급물품())
                    .build();
            extractedDataList.add(extractedData);
        }

        // 필요한 정보 추출하여 엔티티에 저장
        for (GangNamAPIDTO data : dataList) {
            GangNamVenders existingEntity = gangNamVendersRepository.findBy소재지도로명주소(data.get소재지도로명주소());

            if (existingEntity == null) {
                GangNamVenders gangNamEntity = new GangNamVenders();
                gangNamEntity.set관리부서전화번호(data.get관리부서전화번호());
                gangNamEntity.set관리부서명(data.get관리부서명());
                gangNamEntity.set구분(data.get구분());
                gangNamEntity.set데이터기준일자(data.get데이터기준일자());
                gangNamEntity.set소재지도로명주소(data.get소재지도로명주소());
                gangNamEntity.set소재지지번주소(data.get소재지지번주소());
                gangNamEntity.set취급물품(data.get취급물품());

                gangNamVendersRepository.save(gangNamEntity); // 엔티티 저장
            }
        }
        return extractedDataList; // 필요한 정보가 담긴 DTO 리스트 반환
    }
}

