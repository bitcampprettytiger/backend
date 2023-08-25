package com.example.bitcamptiger.seoulApi.controller;

import com.example.bitcamptiger.seoulApi.dto.GangNamAPIDTO;
import com.example.bitcamptiger.seoulApi.dto.GangseoguAPIDTO;
import com.example.bitcamptiger.seoulApi.service.GangseoguAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/API/gangseogu")
public class GangseoguAPIController {

    private final GangseoguAPIService gangseoguAPIService;

    public GangseoguAPIController(GangseoguAPIService gangseoguAPIService) {
        this.gangseoguAPIService = gangseoguAPIService;
    }


    @GetMapping("/GangseoguData")
    public ResponseEntity<List<GangseoguAPIDTO>> getExtractGangseoguData(){
        try {
            // GangseoguAPIService를 이용해 데이터 추출
            List<GangseoguAPIDTO> extractedDataList = gangseoguAPIService.extractGangseoguData();

            // 추출한 데이터가 비어있지 않은 경우
            if (extractedDataList != null && !extractedDataList.isEmpty()) {
                // 추출한 데이터를 성공 응답으로 반환
                return ResponseEntity.ok(extractedDataList);
            } else {
                // 추출한 데이터가 비어있는 경우 서버 내부 에러 응답 반환
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 서버 내부 에러 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}