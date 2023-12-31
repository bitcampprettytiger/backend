package com.example.bitcamptiger.seoulApi.controller;

import com.example.bitcamptiger.gyeongGiApi.service.gyeonggiValidationService.GyeonggiValiService;
import com.example.bitcamptiger.seoulApi.dto.APILocationDTO;
import com.example.bitcamptiger.seoulApi.service.userValidaionService.UserValiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API")
public class APIController {

    @Autowired
    private UserValiService userValiService;
    @Autowired
    private GyeonggiValiService gyeonggiValiService;

    @Operation(summary = "validateByRegion", description = "지역별 노점 유효성 검사")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/validateByRegion")
    public ResponseEntity<String> validateByRegion(@RequestBody APILocationDTO locationDTO) {
        String region = locationDTO.getRegion();
        String value1 = locationDTO.getValue1();
        String value2 = locationDTO.getValue2();

        boolean isValid = false;
        String validationMessage = "";

        String 호점 = extract호점FromLocation(value1); // 호점 정보 추출
        switch (region) {
            case "강남구":
                isValid = userValiService.signUpForGangNam(value1);
                break;
            case "동작구":
                isValid = userValiService.signUpForDongJak(value1, value2);
                break;
            case "노량진":
                isValid = userValiService.signUpForNoRyangJin(호점);
                break;
            case "도봉구":
                isValid = userValiService.signUpForDoBongGu(value1, value2);
                break;
            case "동대문":
                isValid = userValiService.signUpForDongDaeMun(value1, value2);
                break;
            case "강서구":
                isValid = userValiService.signUpForGangseo(value1, value2);
                break;
            case "경기도":
                isValid = gyeonggiValiService.signUpForGyeongGi(value1, value2);
                break;

            // 다른 지역 케이스 추가 ...
            default:
                // 유효하지 않은 지역 처리 ...
                validationMessage = "유효한 정보가 확인되지 않습니다. 다시 확인해주세요.";
                break;
        }

        if (isValid) {
            return ResponseEntity.ok("정보가 일치합니다.");
        } else {
            if (!validationMessage.isEmpty()) {
                return ResponseEntity.badRequest().body(validationMessage);
            } else {
                return ResponseEntity.badRequest().body("정보가 일치하지 않습니다. 다시 확인해주세요.");
            }
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

