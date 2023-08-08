package com.example.bitcamptiger.vendor.service;


import com.example.bitcamptiger.vendor.dto.ValidationResponseDto;
import com.example.bitcamptiger.vendor.dto.VendorValidationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface VendorValidationService {

    // 사업자 정보를 확인하는 메소드
    ValidationResponseDto isValidBusinessInfo(VendorValidationDto vendorValidationDto) throws JsonProcessingException, JSONException;

}

