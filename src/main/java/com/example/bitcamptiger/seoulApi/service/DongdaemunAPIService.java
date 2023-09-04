package com.example.bitcamptiger.seoulApi.service;

import com.example.bitcamptiger.seoulApi.dto.DongdaemunAPIDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface DongdaemunAPIService {
    List<DongdaemunAPIDTO> extractDongdaemunData() throws JsonProcessingException;
}
