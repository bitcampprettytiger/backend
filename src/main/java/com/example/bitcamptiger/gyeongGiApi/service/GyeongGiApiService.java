package com.example.bitcamptiger.gyeongGiApi.service;

import com.example.bitcamptiger.gyeongGiApi.dto.RowDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface GyeongGiApiService {
    List<RowDTO> getAllStreetVendorInfo() throws JsonProcessingException;
}
