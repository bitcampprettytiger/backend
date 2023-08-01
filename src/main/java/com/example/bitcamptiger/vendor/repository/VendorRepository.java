package com.example.bitcamptiger.vendor.repository;

import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.entity.VendorOpenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    List<Vendor> findByVendorName(String vendorName);

    List<Vendor> findByVendorOpenStatus(VendorOpenStatus vendorOpenStatus);
}
