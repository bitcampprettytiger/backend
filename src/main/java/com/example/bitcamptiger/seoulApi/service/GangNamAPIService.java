package com.example.bitcamptiger.seoulApi.service;

import com.example.bitcamptiger.seoulApi.dto.GangNamAPIDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface GangNamAPIService {
    List<GangNamAPIDTO> extractGangNamData() throws JsonProcessingException;
}
