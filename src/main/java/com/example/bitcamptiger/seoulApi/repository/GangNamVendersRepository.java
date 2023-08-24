package com.example.bitcamptiger.seoulApi.repository;

import com.example.bitcamptiger.seoulApi.entity.GangNamVenders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GangNamVendersRepository extends JpaRepository<GangNamVenders,Long> {
    GangNamVenders findBy소재지도로명주소(String 소재지도로명주소);
}
