package com.example.bitcamptiger.seoulApi.repository;

import com.example.bitcamptiger.seoulApi.entity.DongJakVenders;
import com.example.bitcamptiger.seoulApi.entity.DongdaemunVenders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DongdaemunVendersRepository extends JpaRepository<DongdaemunVenders, Long> {
    DongdaemunVenders findBy연번(String 연번);

    DongdaemunVenders findBy거리가게명And주소(String 거리가게명, String 주소);
}
