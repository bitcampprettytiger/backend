package com.example.bitcamptiger.seoulApi.service;

import com.example.bitcamptiger.seoulApi.dto.GangseoguAPIDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface GangseoguAPIService {
    List<GangseoguAPIDTO> extractGangseoguData() throws JsonProcessingException;
}
