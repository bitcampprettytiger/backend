package com.example.bitcamptiger.vendor.service;

import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.VendorOpenStatus;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getVendorList(String vendorName);

    List<VendorDTO> getOpenList(String vendorOpenStatus);

    void insertVendor(VendorDTO vendorDTO);

    void updateVendor(VendorDTO vendorDTO);

    void deleteVendor(VendorDTO vendorDTO);
}
