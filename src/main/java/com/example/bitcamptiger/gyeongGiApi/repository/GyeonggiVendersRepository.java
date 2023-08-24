package com.example.bitcamptiger.gyeongGiApi.repository;


import com.example.bitcamptiger.gyeongGiApi.entity.GyeonggiVenders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GyeonggiVendersRepository extends JpaRepository <GyeonggiVenders, Long> {

}
