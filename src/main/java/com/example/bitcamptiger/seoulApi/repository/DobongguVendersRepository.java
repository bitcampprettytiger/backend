package com.example.bitcamptiger.seoulApi.repository;

import com.example.bitcamptiger.seoulApi.entity.DobongguVenders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DobongguVendersRepository extends JpaRepository<DobongguVenders, Long> {
    DobongguVenders findBy연번And지정번호(String 연번, String 지정번호);

    DobongguVenders findBy도로주소And지정번호(String 도로주소, String 지정번호);
}
