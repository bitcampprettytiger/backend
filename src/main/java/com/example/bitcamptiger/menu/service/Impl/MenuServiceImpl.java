package com.example.bitcamptiger.menu.service.Impl;

import com.example.bitcamptiger.common.FileUtils;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.entity.Menu;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuImageRepository menuImageRepository;
    private final VendorRepository vendorRepository;
    private final FileUtils fileUtils;

    //메뉴 리스트
    @Override
    public List<MenuDTO> getMenuList(Long vendorId) {
        //vendor id 찾아오기
        Optional<Vendor> byId = vendorRepository.findById(vendorId);

        List<Menu> menuList = menuRepository.findByVendor(byId.get());

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


        //Menu 엔티티 생성 후 저장.
        Menu save = menuRepository.save(menuDTO.createMenu());

        //attachPath 경로로 File 객체 생성
        File directory = new File(attachPath);

        if(!directory.exists()){
            directory.mkdirs();
        }

        List<MenuImage> uploadFileList = new ArrayList<>();

        //메뉴 이미지 아이디 저장할 변수를 초기화
        Long imageId = 1L;

        //파일 처리
        for(MultipartFile file : uploadFiles){

            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
                MenuImage menuImage = fileUtils.parseFileInfo(file, attachPath);

                //이전에 저장된 메뉴 정보를 설정.
                menuImage.setMenu(save);
                //menuImage 객체에 imageId 설정.
                menuImage.setId(imageId);

                uploadFileList.add(menuImage);
                //다음 이미지에 사용될 id값 증가.
                imageId = imageId + 1L;
            }

        }

        for(MenuImage menuImage : uploadFileList){
            menuImageRepository.save(menuImage);
        }

    }



    //메뉴 수정
    @Override
    public void updateMenu(MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException {

        // MenuDTO에서 id로 기존의 Menu 엔티티를 찾음
        Menu menu = menuRepository.findById(menuDTO.getId()).orElseThrow(EntityNotFoundException::new);

        //수정 가능한 필드만 업데이트
        menu.setMenuName(menuDTO.getMenuName());
        menu.setPrice(menuDTO.getPrice());
        menu.setMenuContent(menuDTO.getMenuContent());
        menu.setMenuSellStatus(MenuSellStatus.valueOf(menuDTO.getMenuSellStatus()));
        menu.setMenuType(menuDTO.getMenuType());

        //기존 이미지 삭제
        List<MenuImage> existingImages = menu.getImages();
        Long lastImageId = 1L;
        if(existingImages != null && !existingImages.isEmpty()){
            for(MenuImage menuImage : existingImages){
                //가장 큰 menu_img_id 값을 찾는다.
                if(menuImage.getId() > lastImageId){
                    lastImageId = menuImage.getId();
                }
                //s3에서 이미지 삭제
                fileUtils.deleteImage("springboot", menuImage.getUrl() + menuImage.getFileName());

                //db에서 이미지 삭제
                menuImageRepository.delete(menuImage);
            }
            //메뉴 객체에서 이미지 목록 삭제
            menu.getImages().clear();
        }


        //새로운 이미지 리스트 업데이트
        List<MenuImage> uploadFileList = new ArrayList<>();
        for(MultipartFile file : uploadFiles){
            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
                MenuImage menuImage = fileUtils.parseFileInfo(file, attachPath);
                menuImage.setMenu(menu);

                //새로운 menuImage 객체에 imageId 설정
                lastImageId = lastImageId + 1L;
                menuImage.setId(lastImageId);
                uploadFileList.add(menuImage);
            }
        }

        //새로운 이미지 객체들을 메뉴이미지 데이터베이스에 저장
        for(MenuImage menuImage : uploadFileList){
            menuImageRepository.save(menuImage);
        }

    }


    //메뉴 삭제
    @Override
    public void deleteMenu(MenuDTO menuDTO) {

        // MenuDTO에서 id로 기존의 Menu 엔티티를 찾음
        Menu menu = menuRepository.findById(menuDTO.getId()).orElseThrow(EntityNotFoundException::new);

        //메뉴에 연결된 이미지도 함께 삭제
        for(MenuImage menuImage : menu.getImages()){

            //s3에서 이미지 삭제
            fileUtils.deleteImage("springboot", menuImage.getUrl() + menuImage.getFileName());
            //db에서 이미지 삭제
            menuImageRepository.delete(menuImage);

        }

        menuRepository.delete(menu);
    }


}
