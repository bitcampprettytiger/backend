package com.example.bitcamptiger.favoritePick.service;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavoriteService {
    // 찜하기 기능 추가
    @Transactional
    void addFavoriteVendor(Member member, Vendor vendor);

    // 내 찜하기에서 삭제하기
    @Transactional
    void removeFavoriteVendor(Member member, Vendor vendor);

    // 특정 가게 찾는 메소드
    @Transactional
    Vendor getVendorById(Long vendorId);

    // 탑 8 가게 정보 가져오기
    @Transactional
    List<Vendor> getTop8FavoriteVendors();
}
