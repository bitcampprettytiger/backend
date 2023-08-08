package com.example.bitcamptiger.board.service;

import com.example.bitcamptiger.board.dto.BusinessResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface VendorAPIService {
    // 사업자 등록 정보를 확인하는 메소드 선언
    BusinessResponseDto checkBusiness(String businessNumber) throws JsonProcessingException;
}