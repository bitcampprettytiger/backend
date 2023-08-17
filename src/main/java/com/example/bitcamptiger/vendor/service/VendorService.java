package com.example.bitcamptiger.vendor.service;

import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getVendorList();

    List<VendorDTO> getOpenList(String vendorOpenStatus);

    List<VendorDTO> getVendorByCategory(String address, String menuName, String vendorName);

    List<VendorDTO> getVendorByVendorType(String vendorType);

    List<VendorDTO> getVendorByMenuType(String menuType);

    void insertVendor(VendorDTO vendorDTO) throws JsonProcessingException;

    void updateVendor(VendorDTO vendorDTO);

    void deleteVendor(VendorDTO vendorDTO);

    Vendor getVendorDetail(Long id);

}
