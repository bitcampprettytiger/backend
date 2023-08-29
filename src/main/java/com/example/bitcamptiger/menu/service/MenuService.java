package com.example.bitcamptiger.menu.service;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MenuService {
    List<MenuDTO> getMenuList(Long vendorId);

    void insertMenu(Member loggedInMember, MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException;

    void updateMenu(Member loggedInMember, MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException;

    void deleteMenu(MenuDTO menuDTO);

    List<String> getRecommendedMenuTypes();
}
