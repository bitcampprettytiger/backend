package com.example.bitcamptiger.menu.service;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MenuService {
    List<MenuDTO> getMenuList(Long vendorId);

    void insertMenu(Member member, MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException;

    void updateMenu(Member member, MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException;

    void deleteMenu(Member member, MenuDTO menuDTO);

    List<String> getRecommendedMenuTypes();
}
