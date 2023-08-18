package com.example.bitcamptiger.favoritePick.service.impl;

import com.example.bitcamptiger.favoritePick.entity.FavoriteVendor;
import com.example.bitcamptiger.favoritePick.repository.FavoriteVendorRepository;
import com.example.bitcamptiger.favoritePick.service.FavoriteService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    //찜하기와 찜 취소는 해당 가게의 고유 ID와 사용자의 고유 ID를 함께 저장해야함
    private final FavoriteVendorRepository favoriteVendorRepository;
    private final VendorRepository vendorRepository;

    public FavoriteServiceImpl(FavoriteVendorRepository favoriteVendorRepository, VendorRepository vendorRepository) {
        this.favoriteVendorRepository = favoriteVendorRepository;
        this.vendorRepository = vendorRepository;
    }


    //이미 찜한 기록이 없는 경우에만 찜을 추가
    @Transactional
    public void addFavoriteVendor(Member member, Vendor vendor) {
        // 이미 찜한 기록이 있는지 확인
        List<FavoriteVendor> existingFavorites = favoriteVendorRepository.findByMemberAndVendor(member, vendor);

        if (existingFavorites.isEmpty()) {
            FavoriteVendor favoriteVendor = FavoriteVendor.builder()
                    .member(member)
                    .vendor(vendor)
                    .build();
            favoriteVendorRepository.save(favoriteVendor);
        }
    }

    //내 찜하기에서 삭제하기
    @Transactional
    public void removeFavoriteVendor(Member member, Vendor vendor) {
        favoriteVendorRepository.deleteByMemberAndVendor(member, vendor);
    }


    //특정가게 찾는 메소드
    @Transactional
    public Vendor getVendorById(Long vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }

    //// 가장 많이 찜받은 가게들을 조회하여 상위 8개 가게를 반환하는 메서드
    @Transactional
    public List<Vendor> getTop8FavoriteVendors() {
        Map<Vendor, Long> vendorToCountMap = favoriteVendorRepository.findAll().stream()
                .collect(Collectors.groupingBy(FavoriteVendor::getVendor, Collectors.counting())); // 가게별 찜 횟수 계산

        return vendorToCountMap.entrySet().stream()
                .sorted(Map.Entry.<Vendor, Long>comparingByValue().reversed()) // 찜 횟수 기준으로 내림차순 정렬
                .limit(8) // 상위 8개 선택
                .map(Map.Entry::getKey) // 가게 정보만 추출
                .collect(Collectors.toList());
    }
}