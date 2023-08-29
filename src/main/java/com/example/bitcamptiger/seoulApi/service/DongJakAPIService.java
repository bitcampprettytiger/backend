package com.example.bitcamptiger.seoulApi.service;

import com.example.bitcamptiger.seoulApi.dto.DongJakAPIDTO;
import com.example.bitcamptiger.seoulApi.dto.DongJakAPIResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface DongJakAPIService {

    List<DongJakAPIDTO> extractDongJakData() throws JsonProcessingException;
}
