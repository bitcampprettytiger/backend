package com.example.bitcamptiger.menu.repository;

import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuId;
import com.example.bitcamptiger.menu.entity.MenuSellStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface MenuRepository extends JpaRepository<Menu, MenuId> {

    List<Menu> findByVendorId(Long vendorId);
}
