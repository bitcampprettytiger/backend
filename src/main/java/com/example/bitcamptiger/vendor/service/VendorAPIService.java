package com.example.bitcamptiger.vendor.service;

import com.example.bitcamptiger.vendor.dto.BusinessResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface VendorAPIService {
    // 사업자 등록 정보를 확인하는 메소드 선언
    BusinessResponseDto checkBusiness(String businessNumber) throws JsonProcessingException;
}