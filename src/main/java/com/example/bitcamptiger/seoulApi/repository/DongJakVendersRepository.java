package com.example.bitcamptiger.seoulApi.repository;

import com.example.bitcamptiger.seoulApi.entity.DongJakVenders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongJakVendersRepository extends JpaRepository<DongJakVenders, Long> {

    List<DongJakVenders> findBy거리가게명And위치(String 거리가게명, String 위치);

    List<DongJakVenders> findBy구분Containing(String 노량진);

    List<DongJakVenders> findBy구분ContainingAnd위치Containing(String 노량진, String s);
}
