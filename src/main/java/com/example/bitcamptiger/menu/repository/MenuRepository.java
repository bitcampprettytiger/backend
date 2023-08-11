package com.example.bitcamptiger.menu.repository;

import com.example.bitcamptiger.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByVendorId(Long vendorId);
}
