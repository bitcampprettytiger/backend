package com.example.bitcamptiger.seoulApi.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.seoulApi.dto.GangNamAPIDTO;
import com.example.bitcamptiger.seoulApi.entity.GangNamVenders;
import com.example.bitcamptiger.seoulApi.service.GangNamAPIService;
import com.example.bitcamptiger.seoulApi.service.userValidaionService.UserValiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/gangNam")
public class GangNamAPIController {
    private final GangNamAPIService gangNamAPIService;
    private final UserValiService userValiService;

    @Autowired
    public GangNamAPIController(GangNamAPIService gangNamAPIService, UserValiService userValiService) {
        this.gangNamAPIService = gangNamAPIService;
        this.userValiService = userValiService;

    }

    @GetMapping("/GangNamData")
    public ResponseEntity<List<GangNamAPIDTO>> getExtractGangNamData() {
        try {
            // GangNamAPIService를 이용해 데이터 추출
            List<GangNamAPIDTO> extractedDataList = gangNamAPIService.extractGangNamData();

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


    @PostMapping("/validateGangNam")
    public ResponseEntity<?> validateUserAddress(@RequestBody GangNamAPIDTO gangNamAPIDTO) {

        String userAddress = gangNamAPIDTO.get소재지도로명주소();

        if (userAddress == null) {
            userAddress = gangNamAPIDTO.get소재지지번주소();
        }

        if (userValiService.signUpForGangNam(userAddress)) {
            return ResponseEntity.ok().body("일치합니다.");
        } else {
            return ResponseEntity.badRequest().body("일치하지 않습니다.");
        }



//        // 소재지도로명주소가 입력되지 않은 경우에만 소재지지번주소를 사용
//        if (userAddress == null || userAddress.isEmpty()) {
//            userAddress = gangNamAPIDTO.get소재지지번주소();
//        }

//        /*String validationMessage = userValiService.signUpForGangNam(userAddress);*/
//
//        if (userValiService.signUpForGangNam(userAddress)) {
//            // 주소가 일치하는 경우, 회원 가입 다음 단계로 진행
//            return ResponseEntity.ok().body("일치합니다.");
//        } else {
//            // 주소가 일치하지 않는 경우, 에러 처리
//            return ResponseEntity.badRequest().body("일치하지 않습니다.");
//        }


    }







}
