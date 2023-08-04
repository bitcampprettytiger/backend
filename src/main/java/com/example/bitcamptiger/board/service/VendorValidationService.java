package com.example.bitcamptiger.board.service;


import com.example.bitcamptiger.board.dto.ValidationResponseDto;
import com.example.bitcamptiger.board.dto.VendorValidationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface VendorValidationService {

    // 사업자 정보를 확인하는 메소드
    ValidationResponseDto isValidBusinessInfo(VendorValidationDto vendorValidationDto) throws JsonProcessingException, JSONException;

}

