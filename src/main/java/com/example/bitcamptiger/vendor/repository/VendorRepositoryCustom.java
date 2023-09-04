package com.example.bitcamptiger.vendor.repository;

import com.example.bitcamptiger.vendor.dto.NowLocationDto;
import com.example.bitcamptiger.vendor.entity.Vendor;

import java.util.List;

public interface VendorRepositoryCustom {

    List<Vendor> findVendorByCategory(String address, String menuName, String vendorName, String orderBy);

    List<Vendor> findVendorByvendorType(String vendorType);

    List<Vendor> findMenuByCategory(String menuType);

    List<Vendor> findByReviewScore(Long reviewCount);

    List<Vendor> findByrandmart(NowLocationDto nowLocationDto);
}