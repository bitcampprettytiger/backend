package com.example.bitcamptiger.seoulApi.service.impl;

import com.example.bitcamptiger.seoulApi.dto.GangseoguAPIDTO;
import com.example.bitcamptiger.seoulApi.dto.GangseoguAPIResponseDTO;
import com.example.bitcamptiger.seoulApi.entity.GangseoguVenders;
import com.example.bitcamptiger.seoulApi.repository.GangseoguVendersRepository;
import com.example.bitcamptiger.seoulApi.service.GangseoguAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class GangseoguAPIServiceImpl implements GangseoguAPIService {

    @Value("${gangseogu.url}")
    private String apiUrl;

    @Value("${gangseogu.serviceKey}")
    private String apiKey;

    @Autowired
    GangseoguVendersRepository gangseoguVendersRepository;

    @Override
    public List<GangseoguAPIDTO> extractGangseoguData() throws JsonProcessingException {
        List<GangseoguAPIDTO> extractedDataList = new ArrayList<>();

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
        GangseoguAPIResponseDTO responseDto = objectMapper.readValue(response.getBody(), GangseoguAPIResponseDTO.class);

        // GangseoguAPIDTO 리스트 추출
        List<GangseoguAPIDTO> dataList = responseDto.getData();

        // 필요한 정보 추출하여 리스트에 추가
        for (GangseoguAPIDTO data : dataList) {
            GangseoguAPIDTO extractedData = GangseoguAPIDTO.builder()
                    // 여기서 필요한 정보 추출하여 객체를 생성하고 리스트에 추가
                    .거리가게_유형(data.get거리가게_유형())
                    .데이터_기준일자(data.get데이터_기준일자())
                    .비고(data.get비고())
                    .시군구명(data.get시군구명())
                    .위치(data.get위치())
                    .판매품목(data.get판매품목())
                    .허가기간(data.get허가기간())
                    .build();

            extractedDataList.add(extractedData);
        }
        // 필요한 정보 추출하여 엔티티에 저장
        for (GangseoguAPIDTO data : dataList) {
            GangseoguVenders existingEntity = gangseoguVendersRepository.findBy위치And판매품목(data.get위치(),data.get판매품목());

            if (existingEntity == null) {
                GangseoguVenders gangseoguEntity = new GangseoguVenders();
                gangseoguEntity.set거리가게_유형(data.get거리가게_유형());
                gangseoguEntity.set데이터_기준일자(data.get데이터_기준일자());
                gangseoguEntity.set비고(data.get비고());
                gangseoguEntity.set시군구명(data.get시군구명());
                gangseoguEntity.set위치(data.get위치());
                gangseoguEntity.set판매품목(data.get판매품목());
                gangseoguEntity.set허가기간(data.get허가기간());

                gangseoguVendersRepository.save(gangseoguEntity); // 엔티티 저장
            }
        }

        return extractedDataList; // 필요한 정보가 담긴 DTO 리스트 반환
    }
}




