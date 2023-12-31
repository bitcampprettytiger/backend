package com.example.bitcamptiger.vendor.controller;

import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.dto.BusinessResponseDto;
import com.example.bitcamptiger.vendor.dto.ValidationResponseDto;
import com.example.bitcamptiger.vendor.dto.VendorValidationDto;
import com.example.bitcamptiger.vendor.service.VendorAPIService;
import com.example.bitcamptiger.vendor.service.VendorValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/businessApi")
public class RegistrationController {

    //사업자번호 조회 관련 컨트롤러

    private final VendorAPIService vendorAPIService;
//    private final VendorValidationService vendorValidationService;


    @Operation(summary = "checkBusinessNumber", description = "사업자등록번호 유효성 검사")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    //사업자 번호조회 및 유효한 사업자인지 여부에 대한 정보
    @GetMapping("/checkBusiness/{name}")

    public ResponseEntity<?> checkBusiness(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String name
    ) throws JsonProcessingException {
        // 로그인한 사용자의 정보에 접근 (필요한 경우)
        Member loggedInMember = customUserDetails.getUser();
        System.out.println(loggedInMember);

        // VendorAPIService를 사용하여 비즈니스 유효성 검사 API 호출
        BusinessResponseDto responseDto = vendorAPIService.checkBusiness(name);

        // API 호출 결과를 반환
        return ResponseEntity.ok(responseDto);
    }






    //사업자번호와 진위확인 여부 (사업자이름, 상호명, 주소 등등)
//    @PostMapping("/isValidBusinessInfo")
//    public ResponseEntity<?> isValidBusinessInfo(@RequestBody VendorValidationDto vendorValidationDto) throws JsonProcessingException, JSONException {
//        System.out.println(vendorValidationDto);
//        // VendorValidationService 사용하여 비즈니스 유효성 검사 API 호출
//        ValidationResponseDto responseDto = vendorValidationService.isValidBusinessInfo(vendorValidationDto);
//
//        // API 호출 결과를 반환
//        return ResponseEntity.ok(responseDto);
//    }


    // JSON 데이터를 매핑하기 위한 POJO 클래스를 정의합니다.
//    public static class BusinessData {
//        private String[] b_no;
//
//        public String[] getB_no() {
//            return b_no;
//        }
//
//        public void setB_no(String[] b_no) {
//            this.b_no = b_no;
//        }
//    }
}

