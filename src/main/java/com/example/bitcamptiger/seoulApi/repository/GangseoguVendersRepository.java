package com.example.bitcamptiger.seoulApi.repository;

import com.example.bitcamptiger.seoulApi.entity.GangseoguVenders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GangseoguVendersRepository extends JpaRepository<GangseoguVenders, Long> {

    GangseoguVenders findBy위치(String 위치);

    GangseoguVenders findBy위치And판매품목(String 위치, String 판매품목);
}
