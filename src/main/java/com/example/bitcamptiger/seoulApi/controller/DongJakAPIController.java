package com.example.bitcamptiger.seoulApi.controller;

import com.example.bitcamptiger.seoulApi.dto.DongJakAPIDTO;
import com.example.bitcamptiger.seoulApi.service.DongJakAPIService;
import com.example.bitcamptiger.seoulApi.service.userValidaionService.UserValiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/dongJak")
public class DongJakAPIController {
    private final DongJakAPIService dongJakAPIService;
    private final UserValiService userValiService;
    public DongJakAPIController(DongJakAPIService dongJakAPIService, UserValiService userValiService) {
        this.dongJakAPIService = dongJakAPIService;
        this.userValiService = userValiService;
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




    @PostMapping("/validateDongJak")
    public ResponseEntity<String> validateDongJakInfo(@RequestBody DongJakAPIDTO dongJakAPIDTO) {
        // 요청에서 받은 정보 추출
        String storeName = dongJakAPIDTO.get거리가게명();
        String location = dongJakAPIDTO.get위치();

        // 서비스를 통해 정보 검증
        boolean isInformationValid = userValiService.signUpForDongJak(storeName, location);

        if (isInformationValid) {
            // 정보가 일치하는 경우, 성공 응답 반환
            return ResponseEntity.ok("정보가 일치합니다.");
        } else {
            // 정보가 일치하지 않는 경우, 실패 응답 반환
            return ResponseEntity.badRequest().body("정보가 일치하지 않습니다. 다시 확인해주세요.");
        }
    }

    //사용자가 "노량 선택하고 호점 정보를 입력한 경우에 대한 검증 로직
    //사용자가 입력한 호점 정보를 추출하여 "노량진"이 구분에 포함되는 엔티티 중 호점 정보와 일치하는 것이 있는지 확인하고,
    // 그에 따라 적절한 응답을 반환하는 것
    @PostMapping("/NoRyangJin")
    public ResponseEntity<String> validateNoRyangJinInfo(
            @RequestBody DongJakAPIDTO dongJakAPIDTO) {
        String location = dongJakAPIDTO.get위치();
        // 사용자가 입력한 호점 정보를 추출하여 검증합니다.
        String 호점 = extract호점FromLocation(location);

        boolean isInformationValid = userValiService.signUpForNoRyangJin(호점);

        if (isInformationValid) {
            // 정보가 일치하는 경우, 성공 응답 반환
            return ResponseEntity.ok("정보가 일치합니다.");
        } else {
            // 정보가 일치하지 않는 경우, 실패 응답 반환
            return ResponseEntity.badRequest().body("정보가 일치하지 않습니다. 다시 확인해주세요.");
        }
    }

    private String extract호점FromLocation(String location) {
        String[] tokens = location.split(" "); // 위치 문자열을 공백을 기준으로 분리

        for (String token : tokens) {
            if (token.endsWith("호점")) { // "호점"으로 끝나는 토큰
                String 호점 = token.replace("호점", ""); // "호점"을 제거한 숫자 추출
                if (호점.matches("\\d+")) { // 추출한 숫자가 숫자로만 이루어진 경우에만 반환
                    return 호점;
                }
            }
        }

        return null; // 호점을 찾지 못한 경우에는 null을 반환
    }
}
