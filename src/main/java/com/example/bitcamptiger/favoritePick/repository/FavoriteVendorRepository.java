package com.example.bitcamptiger.favoritePick.repository;

import com.example.bitcamptiger.favoritePick.entity.FavoriteVendor;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteVendorRepository extends JpaRepository<FavoriteVendor, Long> {


//    save(FavoriteVendor favoriteVendor);


    void deleteByMemberAndVendor(Member member, Vendor vendor);

}
