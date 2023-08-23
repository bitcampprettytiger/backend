package com.example.bitcamptiger.seoulApi.repository;

import com.example.bitcamptiger.seoulApi.entity.DongJakVenders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DongJakVendersRepository extends JpaRepository<DongJakVenders, Long> {

}
