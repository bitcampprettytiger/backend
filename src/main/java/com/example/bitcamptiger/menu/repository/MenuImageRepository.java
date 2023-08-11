package com.example.bitcamptiger.menu.repository;

import com.example.bitcamptiger.menu.entity.MenuImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface MenuImageRepository extends JpaRepository<MenuImage, Long> {


    Optional<MenuImage> findById(Long menuImgId);

}
