package com.example.bitcamptiger.vendor;

import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.service.VendorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class testtestCode {
    @Autowired
    private VendorService vendorService;

    @Test
    @Transactional
    public void testInsertVendor() throws JsonProcessingException {
        // 준비: VendorDTO를 생성하고 필요한 데이터를 설정
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setAddress("...");
        vendorDTO.setB_no("...");
        vendorDTO.setPerNo("...");
        vendorDTO.setRlAppiNm("...");

        // 테스트: insertVendor 메서드를 실행하여 테스트
        vendorService.insertVendor(vendorDTO);

        // 어설션: 테스트 후 원하는 상태나 결과를 검증
        // 이 부분에 원하는 검증 로직을 추가하세요
    }

}
