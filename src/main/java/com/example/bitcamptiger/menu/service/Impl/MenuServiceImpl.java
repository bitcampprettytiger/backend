package com.example.bitcamptiger.menu.service.Impl;

import com.example.bitcamptiger.common.FileUtils;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.menu.entity.MenuImageId;
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


    @Value("${file.path}")
    String attachPath;



    //메뉴 등록
    @Override
    public void insertMenu(MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException {

        Menu save = menuRepository.save(menuDTO.createMenu());
        //변경사항 커밋 후 저장
        menuRepository.flush();


        MenuImageId id = new MenuImageId();
        id.setMenu(save);
        id.setId(1L);


        File directory = new File(attachPath);

        if(!directory.exists()){
            directory.mkdirs();
        }

        List<MenuImage> uploadFileList = new ArrayList<>();

        Long imageid = 1L;
        //파일 처리
        for(MultipartFile file : uploadFiles){

            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
                MenuImage menuImage = FileUtils.parseFileInfo(file, attachPath);

                //여기여기여기여기==============================================================
                menuImage.setMenu(save);
                menuImage.setId(imageid);

                uploadFileList.add(menuImage);
               imageid = imageid + 1L;
            }

        }

        for(MenuImage menuImage : uploadFileList){
            menuImageRepository.save(menuImage);
//            menuImageRepository.flush();
        }

//        menuRepository.flush();
    }



    //메뉴 수정
    @Override
    public void updateMenu(MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException {
//        MenuId menuId = new MenuId();
//        Menu menu = new Menu();
//        menu.setId(menuDTO.getId());
//        menu.setVendor(menuDTO.getVendor().getId());

        // MenuDTO에서 id로 기존의 Menu 엔티티를 찾음
        Menu menu = menuRepository.findById(menuDTO.getId()).orElseThrow(EntityNotFoundException::new);

        // VendorDTO에서 Vendor 엔티티로 변환
        Vendor vendor = vendorRepository.findById(menuDTO.getVendor().getId()).orElseThrow(EntityNotFoundException::new);

        //수정 가능한 필드만 업데이트
        menu.setMenuName(menuDTO.getMenuName());
        menu.setPrice(menuDTO.getPrice());
        menu.setMenuContent(menuDTO.getMenuContent());
        menu.setMenuSellStatus(MenuSellStatus.valueOf(menuDTO.getMenuSellStatus()));
        menu.setMenuType(menuDTO.getMenuType());

        //기존 이미지 삭제
//        menu.getImages().clear();

        //이미지 리스트 업데이트
        List<MenuImage> uploadFileList = new ArrayList<>();
        for(MultipartFile file : uploadFiles){
            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
                MenuImage menuImage = FileUtils.parseFileInfo(file, attachPath);
                menuImage.setMenu(menu);
                uploadFileList.add(menuImage);
            }
        }
//        menu.getImages().addAll(uploadFileList);

        menuRepository.save(menu);

    }


    //메뉴 삭제
    @Override
    public void deleteMenu(MenuDTO menuDTO) {
//        MenuId menuId = new MenuId();
//        menuId.setId(menuDTO.getId());
//        menuId.setVendor(menuDTO.getVendor().getId());

        Menu menu = menuRepository.findById(menuDTO.getId()).orElseThrow(EntityNotFoundException::new);

        //메뉴에 연결된 이미지도 함께 삭제
//        for(MenuImage menuImage : menu.getImages()){
//            menuImageRepository.delete(menuImage);
//        }

        menuRepository.delete(menu);
    }


}
