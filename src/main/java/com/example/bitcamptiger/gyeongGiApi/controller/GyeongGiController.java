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
@RequestMapping("/api/gyeonggi")
public class GyeongGiController {
    private final GyeongGiApiService gyeongGiApiService;

    @Autowired
    public GyeongGiController(GyeongGiApiService gyeongGiApiService) {
        this.gyeongGiApiService = gyeongGiApiService;
    }



    @GetMapping("/vendors")
    public List<RowDTO> getAllVendors() throws JsonProcessingException {
        ResponseDTO<List<Map<String, Object>>> responseDTO = new ResponseDTO<>();
        return gyeongGiApiService.getAllStreetVendorInfo();
    }



//    @GetMapping("/activeVendors")
//    public List<GGStreetVendorResponseDTO> getActiveVendors() throws JsonProcessingException {
//        // 노점상 정보를 가져와서 폐업여부가 'N'인 정보만 필터링하여 반환
//
//        // gyeongGiApiService.getAllStreetVendorInfo() 메서드로부터 노점상 정보를 가져옵니다.
//        List<GGStreetVendorResponseDTO> allVendors = gyeongGiApiService.getAllStreetVendorInfo();
//
//        // 만약 allVendors 리스트가 null인 경우에 대한 예외 처리
//        if (allVendors == null) {
//            System.out.println("allVendors 리스트가 null입니다.");
//            return Collections.emptyList(); // 빈 리스트를 반환
//        }
//
//        // 폐업여부가 'N'인 노점상 정보만 필터링하여 새로운 리스트로 반환합니다.
//        return allVendors.stream()
//                .filter(vendor -> "N".equals(vendor.getClosedBusiness()))
//                .collect(Collectors.toList());
//    }
}
