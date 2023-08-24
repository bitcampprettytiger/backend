package com.example.bitcamptiger.seoulApi.controller;

import com.example.bitcamptiger.seoulApi.dto.DongJakAPIDTO;
import com.example.bitcamptiger.seoulApi.dto.DongJakAPIResponseDTO;
import com.example.bitcamptiger.seoulApi.service.DongJakAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/API/DongJak")
public class DongJakAPIController {
    private final DongJakAPIService dongJakAPIService;

    public DongJakAPIController(DongJakAPIService dongJakAPIService) {
        this.dongJakAPIService = dongJakAPIService;
    }


    @GetMapping("/DongJakData")
    public ResponseEntity<List<DongJakAPIDTO>> getExtractedDongJakData() {
        try {
            // DongJakAPIService를 이용해 데이터 추출
            List<DongJakAPIDTO> extractedDataList = dongJakAPIService.extractDongJakData();

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
