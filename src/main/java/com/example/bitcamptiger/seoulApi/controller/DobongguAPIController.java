package com.example.bitcamptiger.seoulApi.controller;

import com.example.bitcamptiger.seoulApi.dto.DobongguAPIDTO;
import com.example.bitcamptiger.seoulApi.service.DobongguAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/API/dobonggu")
public class DobongguAPIController {

    @Autowired
    private DobongguAPIService dobongguAPIService;
    @Operation(summary = "DobongguData", description = "도봉구 노점 유효성 검사")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/DobongguData")
    public ResponseEntity<List<DobongguAPIDTO>> getExtractDobongguData() throws JsonProcessingException {
        try {
            // DobongguAPIService를 이용해 데이터 추출
            List<DobongguAPIDTO> extractedDataList = dobongguAPIService.extractDobongguData();

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
