package com.example.bitcamptiger.vendor.repository;

import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.entity.VendorImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VendorImageRepository extends JpaRepository<VendorImage, Long> {

    List<VendorImage> findByVendor(Vendor vendor);

}
