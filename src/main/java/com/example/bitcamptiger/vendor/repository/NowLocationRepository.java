package com.example.bitcamptiger.vendor.repository;

import com.example.bitcamptiger.vendor.entity.Randmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface NowLocationRepository extends JpaRepository<Randmark,Long>, QuerydslPredicateExecutor<Randmark>,NowLocationRepositoryCustom{

    Optional<Randmark> findByMapLocation(String mapLocation);


}
