package com.example.bitcamptiger.menu.repository;

import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByVendor(Vendor vendor);

    //조회수 높은 메뉴 탑 5 찾기
    List<Menu> findTop10ByOrderByViewsDesc();

    List<Menu> findByMenuType(String menuType);
}
