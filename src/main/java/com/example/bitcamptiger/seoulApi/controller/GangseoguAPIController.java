package com.example.bitcamptiger.seoulApi.controller;

import com.example.bitcamptiger.seoulApi.dto.GangseoguAPIDTO;
import com.example.bitcamptiger.seoulApi.service.GangseoguAPIService;
import com.example.bitcamptiger.seoulApi.service.userValidaionService.UserValiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/gangseogu")
public class GangseoguAPIController {

    private final GangseoguAPIService gangseoguAPIService;
    private final UserValiService userValiService;

    public GangseoguAPIController(GangseoguAPIService gangseoguAPIService, UserValiService userValiService) {
        this.gangseoguAPIService = gangseoguAPIService;
        this.userValiService = userValiService;
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
    
    
    @PostMapping("/validateGangseo")
    public ResponseEntity<?> validateGangseoInfo(@RequestBody GangseoguAPIDTO gangseoguAPIDTO){
        String 위치 = gangseoguAPIDTO.get위치();
        String 판매품목 = gangseoguAPIDTO.get판매품목();
        
        //서비스를 통해 정보 검증
        boolean isInformationValid = userValiService.signUpForGangseo(위치, 판매품목);
        
        if(isInformationValid){
            //정보가 일치하는 경우, 다음 단계로 진행
            return ResponseEntity.ok("정보가 일치합니다.");
        }else{
            //정보가 일치하지 않는 경우, 에러 처리
            return ResponseEntity.badRequest().body("정보가 일치하지 않습니다. 다시 확인해주세요.");
        }
    }

    
    
    
    
}