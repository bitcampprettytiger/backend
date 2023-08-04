package com.example.bitcamptiger.board.controller;

import com.example.bitcamptiger.board.dto.BusinessResponseDto;
import com.example.bitcamptiger.board.dto.ValidationResponseDto;
import com.example.bitcamptiger.board.dto.VendorValidationDto;
import com.example.bitcamptiger.board.service.VendorAPIService;
import com.example.bitcamptiger.board.service.VendorValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    //사업자번호 조회 관련 컨트롤러

    private final VendorAPIService vendorAPIService;
    private final VendorValidationService vendorValidationService;

    @Autowired
    public RegistrationController(VendorAPIService vendorAPIService, VendorValidationService vendorValidationService) {
        this.vendorAPIService = vendorAPIService;
        this.vendorValidationService = vendorValidationService;
    }

    //사업자 번호조회 및 유효한 사업자인지 여부에 대한 정보
    @GetMapping("/checkBusiness/{name}")
    public ResponseEntity<?> checkBusiness(@PathVariable String name) throws JsonProcessingException {
        // VendorAPIService를 사용하여 비즈니스 유효성 검사 API 호출
        BusinessResponseDto responseDto = vendorAPIService.checkBusiness(name);

        // API 호출 결과를 반환
        return ResponseEntity.ok(responseDto);
    }


    //사업자번호와 진위확인 여부 (사업자이름, 상호명, 주소 등등)
    @PostMapping("/isValidBusinessInfo")
    public ResponseEntity<?> isValidBusinessInfo(@RequestBody VendorValidationDto vendorValidationDto) throws JsonProcessingException, JSONException {
        System.out.println(vendorValidationDto);
        // VendorValidationService 사용하여 비즈니스 유효성 검사 API 호출
        ValidationResponseDto responseDto = vendorValidationService.isValidBusinessInfo(vendorValidationDto);

        // API 호출 결과를 반환
        return ResponseEntity.ok(responseDto);
    }


    // JSON 데이터를 매핑하기 위한 POJO 클래스를 정의합니다.
    public static class BusinessData {
        private String[] b_no;

        public String[] getB_no() {
            return b_no;
        }

        public void setB_no(String[] b_no) {
            this.b_no = b_no;
        }
    }
}

