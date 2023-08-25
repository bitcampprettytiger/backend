package com.example.bitcamptiger.gyeongGiApi.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.gyeongGiApi.dto.RowDTO;
import com.example.bitcamptiger.gyeongGiApi.service.GyeongGiApiService;
import com.example.bitcamptiger.gyeongGiApi.service.gyeonggiValidationService.GyeonggiValiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/API/gyeonggi")
public class GyeongGiController {
    private final GyeongGiApiService gyeongGiApiService;
    private final GyeonggiValiService userValiService;

    @Autowired
    public GyeongGiController(GyeongGiApiService gyeongGiApiService, GyeonggiValiService userValiService) {
        this.gyeongGiApiService = gyeongGiApiService;
        this.userValiService = userValiService;
    }


    @GetMapping("/GyeonggiData")
    public List<RowDTO> getAllVendors() throws JsonProcessingException {
        ResponseDTO<List<Map<String, Object>>> responseDTO = new ResponseDTO<>();
        return gyeongGiApiService.getAllStreetVendorInfo();
    }


    @PostMapping("validateGyeonggi")
    public ResponseEntity<?> validateGyeonggiInfo(@RequestBody RowDTO rowDTO){
        String prmsnNm = rowDTO.getPrmsnNm();
        String storeNm = rowDTO.getStoreNm();
        //서비스를 통해 정보 검증
        boolean isInformationValid = userValiService.signUpForGyeongGi(prmsnNm, storeNm);

        if(isInformationValid){
            //정보가 일치하는 경우, 다음 단계로 진행
            return ResponseEntity.ok("정보가 일치합니다.");
        }else{
            //정보가 일치하지 않는 경우, 에러 처리
            return ResponseEntity.badRequest().body("정보가 일치하지 않습니다. 다시 확인해주세요.");
        }
    }


}
