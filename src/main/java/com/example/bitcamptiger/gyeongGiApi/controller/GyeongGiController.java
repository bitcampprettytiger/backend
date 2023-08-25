package com.example.bitcamptiger.gyeongGiApi.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.gyeongGiApi.dto.RowDTO;
import com.example.bitcamptiger.gyeongGiApi.service.GyeongGiApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/gyeonggi")
public class GyeongGiController {
    private final GyeongGiApiService gyeongGiApiService;

    @Autowired
    public GyeongGiController(GyeongGiApiService gyeongGiApiService) {
        this.gyeongGiApiService = gyeongGiApiService;
    }



    @GetMapping("/GyeonggiData")
    public List<RowDTO> getAllVendors() throws JsonProcessingException {
        ResponseDTO<List<Map<String, Object>>> responseDTO = new ResponseDTO<>();
        return gyeongGiApiService.getAllStreetVendorInfo();
    }



}
