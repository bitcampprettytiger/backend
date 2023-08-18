package com.example.bitcamptiger.vendor.service;

import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface VendorService {
    // 모든 가게 목록 가져오기
    List<VendorDTO> getVendorList();

    // 영업 중인 가게 목록 가져오기
    List<VendorDTO> getOpenList(String vendorOpenStatus);

    // 주소에 따른 가게 목록 가져오기
    List<VendorDTO> getVendorByAddressCategory(String address);

    // 가게 정보 추가
    void insertVendor(VendorDTO vendorDTO) throws JsonProcessingException;

    // 가게 정보 업데이트
    void updateVendor(VendorDTO vendorDTO);

    // 가게 정보 삭제
    void deleteVendor(VendorDTO vendorDTO);

    // 특정 가게의 상세 정보 가져오기
    Vendor getVendorDetail(Long id);

}
