package com.example.bitcamptiger.menu.repository;

import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

<<<<<<< HEAD
public interface MenuRepository extends JpaRepository <Menu, Long> {

    List<Menu> findByVendorId(Long vendorId);

    List<Menu> findTop5ByOrderByMenuViewsDesc();
=======
@Transactional
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByVendor(Vendor vendor);
>>>>>>> 423b68f63cca168bf55915eace240daa828404b2
}
