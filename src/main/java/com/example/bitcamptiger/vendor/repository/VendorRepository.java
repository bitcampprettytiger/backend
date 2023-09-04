package com.example.bitcamptiger.vendor.repository;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>,
        QuerydslPredicateExecutor<Vendor>, VendorRepositoryCustom {

    List<Vendor> findByVendorName(String vendorName);

//    Vendor findByMenuList(List<Menu> menuList);

    List<Vendor> findByVendorOpenStatus(String vendorOpenStatus);

    @Query("SELECT v.address FROM Vendor v")
    List<String> findAllAddresses();


    Vendor findByMember(Member member);
}
