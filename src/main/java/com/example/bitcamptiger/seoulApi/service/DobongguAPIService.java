package com.example.bitcamptiger.seoulApi.service;

import com.example.bitcamptiger.seoulApi.dto.DobongguAPIDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface DobongguAPIService {
    List<DobongguAPIDTO> extractDobongguData() throws JsonProcessingException;
}
