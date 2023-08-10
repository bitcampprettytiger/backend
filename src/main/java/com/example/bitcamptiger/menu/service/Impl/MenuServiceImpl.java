package com.example.bitcamptiger.menu.service.Impl;

import com.example.bitcamptiger.common.FileUtils;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuId;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.menu.entity.MenuSellStatus;
import com.example.bitcamptiger.menu.repository.MenuImageRepository;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.menu.service.MenuService;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuImageRepository menuImageRepository;
    private final VendorRepository vendorRepository;


    //메뉴 리스트
    @Override
    public List<MenuDTO> getMenuList(Long vendorId) {

        List<Menu> menuList = menuRepository.findByVendorId(vendorId);

        List<MenuDTO> menuDTOList = new ArrayList<>();

        //menuList의 각 요소에 대해 반복하며, 각각의 Menu 객체를 반복 변수인 "menu"에 할당
        for(Menu menu : menuList){
            //Menu 객체를 MenuDTO 객체로 변환
            MenuDTO menuDTO = MenuDTO.of(menu);

            menuDTOList.add(menuDTO);
        }
        return menuDTOList;
    }

    //메뉴 등록
    @Override
    public void insertMenu(Menu menu, MultipartFile[] uploadFiles) throws IOException {
        menuRepository.flush();
        menuRepository.save(menu);
        //변경사항 커밋 후 저징
        menuRepository.flush();

        File directory = new File(attachPath);

        if(!directory.exists()){
            directory.mkdir();
        }

        List<MenuImage> uploadFileList = new ArrayList<>();

        //파일 처리
        for(MultipartFile file : uploadFiles){

            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
                MenuImage menuImage = FileUtils.parseFileInfo(file, attachPath);

                menuImage.setMenu(menu);

                uploadFileList.add(menuImage);
            }

        }

        for(MenuImage menuImage : uploadFileList){
            menuImageRepository.save(menuImage);
        }

    }

    @Value("${file.path}")
    String attachPath;


    //메뉴 이미지 파일 등록
    public void saveMenuImage(Menu menu, MultipartFile[] uploadFiles) throws IOException {

        File directory = new File(attachPath);

        if(!directory.exists()){
            directory.mkdir();
        }

        List<MenuImage> uploadFileList = new ArrayList<>();

        //파일 처리
        for(MultipartFile file : uploadFiles){

            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
                MenuImage menuImage = FileUtils.parseFileInfo(file, attachPath);

                menuImage.setMenu(menu);

                uploadFileList.add(menuImage);
            }

        }

        for(MenuImage menuImage : uploadFileList){
            menuImageRepository.save(menuImage);
            menuImageRepository.flush();
        }
    }


    //메뉴 수정
    @Override
    public void updateMenu(MenuDTO menuDTO) {
        MenuId menuId = new MenuId();
        menuId.setId(menuDTO.getId());
        menuId.setVendor(menuDTO.getVendor().getId());

        // MenuDTO에서 id로 기존의 Menu 엔티티를 찾음
        Menu menu = menuRepository.findById(menuId).orElseThrow(EntityNotFoundException::new);

        // VendorDTO에서 Vendor 엔티티로 변환
        Vendor vendor = vendorRepository.findById(menuDTO.getVendor().getId()).orElseThrow(EntityNotFoundException::new);

        //수정 가능한 필드만 업데이트
        menu.setMenuName(menuDTO.getMenuName());
        menu.setPrice(menuDTO.getPrice());
        menu.setMenuContent(menuDTO.getMenuContent());
        menu.setMenuSellStatus(MenuSellStatus.valueOf(menuDTO.getMenuSellStatus()));
        menu.setMenuType(menuDTO.getMenuType());
        menu.setVendor(vendor);


        menuRepository.save(menu);

    }

    //메뉴 삭제
    @Override
    public void deleteMenu(MenuDTO menuDTO) {
        MenuId menuId = new MenuId();
        menuId.setVendor(menuDTO.getVendor().getId());
        menuId.setId(menuDTO.getId());

        Menu menu = menuRepository.findById(menuId).orElseThrow(EntityNotFoundException::new);

        menuRepository.delete(menu);
    }


}
