package com.example.bitcamptiger.vendor.repository;

import com.example.bitcamptiger.vendor.entity.Randmark;
import com.example.bitcamptiger.vendor.entity.Vendor;

import java.util.List;

public interface NowLocationRepositoryCustom {

    List<Randmark> findRandmarkBydistinct(Vendor vendor);

}
