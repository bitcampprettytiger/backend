package com.example.bitcamptiger.vendor.repository;

import com.example.bitcamptiger.vendor.entity.Vendor;

import java.util.List;

public interface VendorRepositoryCustom {

    List<Vendor> findVendorByAddressCategory(String address);
}
