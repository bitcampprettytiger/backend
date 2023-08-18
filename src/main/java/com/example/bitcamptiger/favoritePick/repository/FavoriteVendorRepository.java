package com.example.bitcamptiger.favoritePick.repository;

import com.example.bitcamptiger.favoritePick.entity.FavoriteVendor;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteVendorRepository extends JpaRepository<FavoriteVendor, Long> {


//    save(FavoriteVendor favoriteVendor);


    void deleteByMemberAndVendor(Member member, Vendor vendor);

    //찜 중복방지
    List<FavoriteVendor> findByMemberAndVendor(Member member, Vendor vendor);
}
