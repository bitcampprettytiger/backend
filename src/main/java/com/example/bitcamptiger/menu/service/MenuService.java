package com.example.bitcamptiger.menu.service;

import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.entity.Menu;

import java.util.List;

public interface MenuService {
    List<MenuDTO> getMenuList(Long vendorId);


    void insertMenu(MenuDTO menuDTO);

    void updateMenu(MenuDTO menuDTO);

    void deleteMenu(MenuDTO menuDTO);
}
