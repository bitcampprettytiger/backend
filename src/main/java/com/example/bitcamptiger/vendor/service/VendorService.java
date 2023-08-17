package com.example.bitcamptiger.vendor.service;

import com.example.bitcamptiger.vendor.dto.LocationDto;
import com.example.bitcamptiger.vendor.dto.NowLocationDto;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface VendorService {
    NowLocationDto saverandmark(NowLocationDto nowLocationDto);

    List<VendorDTO> getVendorList();

    List<VendorDTO> getOpenList(String vendorOpenStatus);

    List<VendorDTO> getVendorByAddressCategory(String address);

    void insertVendor(VendorDTO vendorDTO) throws JsonProcessingException;

    void updateVendor(VendorDTO vendorDTO);

    void deleteVendor(VendorDTO vendorDTO);

    Vendor getVendorDetail(Long id);

   List<LocationDto> getNowLocationList(NowLocationDto nowLocationDto);
}
