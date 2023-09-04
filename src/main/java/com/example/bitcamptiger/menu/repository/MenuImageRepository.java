package com.example.bitcamptiger.menu.repository;

import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.menu.entity.MenuImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public interface MenuImageRepository extends JpaRepository<MenuImage, MenuImageId> {


    Optional<MenuImage> findById(Long menuImgId);

    List<MenuImage> findByMenu(Menu menu);

    List<MenuImage> findByMenu_Id(Long menuId);

}
