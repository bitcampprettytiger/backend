package com.example.bitcamptiger.menu.service;

import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.entity.Menu;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MenuService {
    List<MenuDTO> getMenuList(Long vendorId);

    void insertMenu(MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException;

    void updateMenu(MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException;

    void deleteMenu(MenuDTO menuDTO);
}
