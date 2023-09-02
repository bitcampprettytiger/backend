package com.example.bitcamptiger.menu.service.Impl;

import com.example.bitcamptiger.common.FileUtils;
import com.example.bitcamptiger.common.service.S3UploadService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.dto.MenuImageDTO;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuImageRepository menuImageRepository;
    private final VendorRepository vendorRepository;
    private final FileUtils fileUtils;
    public  final S3UploadService s3UploadService;


    //메뉴 리스트
    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> getMenuList(Long vendorId) {
        //1. vendor id 찾아오기
        Optional<Vendor> byId = vendorRepository.findById(vendorId);

        List<Menu> menuList = menuRepository.findByVendor(byId.get());

        List<MenuDTO> menuDTOList = new ArrayList<>();
        //menuList의 각 요소에 대해 반복하며, 각각의 Menu 객체를 반복 변수인 "menu"에 할당
        for(Menu menu : menuList){
            //Menu 객체를 MenuDTO 객체로 변환
            MenuDTO menuDTO = MenuDTO.of(menu);


            //2. 해당 메뉴에 대한 모든 메뉴 이미지를 검색
            List<MenuImage> menuImageList = menuImageRepository.findByMenu(menu);
            String geturl = new String();
            List<MenuImageDTO> menuImageDTOList = new ArrayList<>();
            if(!menuImageList.isEmpty()) {
                for (MenuImage menuImage : menuImageList) {
                    geturl = s3UploadService.geturl(menuImage.getUrl() + menuImage.getFileName());
//                menuImage.setUrl(geturl);
                    //MenuImage 객체를 MenuImageDTO 객체로 변환
                    MenuImageDTO menuImageDTO = MenuImageDTO.of(menuImage);
                    menuImageDTO.setUrl(geturl);
                    menuImageDTOList.add(menuImageDTO);
                }
             menuDTO.setPrimaryimage(geturl);
            }

            // MenuDTO 객체에 메뉴 이미지 리스트를 설정
            menuDTO.setMenuImageList(menuImageDTOList);
            System.out.println(menuDTO);
            menuDTOList.add(menuDTO);

        }
        return menuDTOList;
    }


    @Value("${menuFile.path}")
    String attachPath;



    //메뉴 등록
    @Override
    public void insertMenu(Member member, MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException {

        Vendor vendor = vendorRepository.findByMember(member);

        //검증. 로그인한 유저와 vendor의 등록자가 다르면 예외 발생
        if(!vendor.getMember().getId().equals(member.getId())){
            throw new AccessDeniedException("등록 권한이 없습니다.");
        }
        //Menu와 Vendor 연결
        menuDTO.setVendor(vendor);
        //Menu 엔티티 생성 후 저장.
        Menu menu = menuDTO.createMenu();

        Menu save = menuRepository.save(menu);

        //attachPath 경로로 File 객체 생성
        File directory = new File(attachPath);

        if(!directory.exists()){
            directory.mkdirs();
        }

        List<MenuImage> uploadFileList = new ArrayList<>();

        //메뉴 이미지 아이디 저장할 변수를 초기화
        Long imageId = 1L;


        if(uploadFiles != null && uploadFiles.length > 0) {

            //파일 처리
            for (MultipartFile file : uploadFiles) {

                if (file != null && !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
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
        }

        //menuImage가 등록되지 않았을 경우 기본이미지 설정
        if(uploadFileList.isEmpty()) {
            MenuImage defaultMenuImage = fileUtils.getDefaultMenuImage();
            defaultMenuImage.setMenu(save);
            //menuImage 객체에 imageId 설정.
            defaultMenuImage.setId(imageId);
            uploadFileList.add(defaultMenuImage);

            //다음 이미지에 사용될 id값 증가.
            imageId = imageId + 1;
        }


        for(MenuImage menuImage : uploadFileList){
            menuImageRepository.save(menuImage);
        }

    }



    //메뉴 수정
    @Override
    public void updateMenu(Member member, MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException {


        Vendor vendor = vendorRepository.findByMember(member);

        //검증. 로그인한 유저와 vendor의 등록자가 다르면 예외 발생
        if(!vendor.getMember().getId().equals(member.getId())){
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        //Menu와 Vendor 연결
        menuDTO.setVendor(vendor);


        // MenuDTO에서 id로 기존의 Menu 엔티티를 찾음
        Menu menu = menuRepository.findById(menuDTO.getId()).orElseThrow(EntityNotFoundException::new);

        if(!menu.getVendor().getId().equals(vendor.getId())){
            throw new AccessDeniedException("메뉴 수정 권한이 없습니다.");
        }


        //Menu와 Vendor 연결
        menu.setVendor(menuDTO.getVendor());
        //수정 가능한 필드만 업데이트
        menu.setMenuName(menuDTO.getMenuName());
        menu.setPrice(menuDTO.getPrice());
        menu.setMenuContent(menuDTO.getMenuContent());
        menu.setMenuSellStatus(MenuSellStatus.valueOf(menuDTO.getMenuSellStatus()));
        menu.setMenuType(menuDTO.getMenuType());

        //기존 이미지 삭제
        List<MenuImage> existingImages = menuImageRepository.findByMenu(menu);

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

        }


        //새로운 이미지 리스트 업데이트
        List<MenuImage> uploadFileList = new ArrayList<>();
        if(uploadFiles != null && !uploadFiles.equals(null) ) {

            for (MultipartFile file : uploadFiles) {
                if (file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
                    MenuImage menuImage = fileUtils.parseFileInfo(file, attachPath);
                    menuImage.setMenu(menu);

                    //새로운 menuImage 객체에 imageId 설정
                    lastImageId = lastImageId + 1L;
                    menuImage.setId(lastImageId);
                    uploadFileList.add(menuImage);
                }
            }
        }
            //menuImage가 등록되지 않았을 경우 기본이미지 설정
            if (uploadFileList.isEmpty()) {
                MenuImage defaultMenuImage = fileUtils.getDefaultMenuImage();
                defaultMenuImage.setMenu(menu);

                //menuImage 객체에 imageId 설정.
                lastImageId = lastImageId + 1L;
                defaultMenuImage.setId(lastImageId);
                uploadFileList.add(defaultMenuImage);

            }

            //새로운 이미지 객체들을 메뉴이미지 데이터베이스에 저장
            for (MenuImage menuImage : uploadFileList) {
                menuImageRepository.save(menuImage);
            }
    }


    //메뉴 삭제
    @Override
    public void deleteMenu(Member member, MenuDTO menuDTO) {


        Vendor vendor = vendorRepository.findByMember(member);

        //검증. 로그인한 유저와 vendor의 등록자가 다르면 예외 발생
        if(!vendor.getMember().getId().equals(member.getId())){
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        //Menu와 Vendor 연결
        menuDTO.setVendor(vendor);

        // MenuDTO에서 id로 기존의 Menu 엔티티를 찾음
        Menu menu = menuRepository.findById(menuDTO.getId()).orElseThrow(EntityNotFoundException::new);

        if(!menu.getVendor().getId().equals(vendor.getId())){
            throw new AccessDeniedException("메뉴 삭제 권한이 없습니다.");
        }


        //메뉴에 연결된 이미지도 함께 삭제
        for(MenuImage menuImage : menuImageRepository.findByMenu_Id(menu.getId())){

            //s3에서 이미지 삭제
            fileUtils.deleteImage("springboot", menuImage.getUrl() + menuImage.getFileName());
            //db에서 이미지 삭제
            menuImageRepository.delete(menuImage);

        }

        menuRepository.delete(menu);
    }


    //메뉴조회수순 탑5
//조회수가 높은 순서대로 상위 5개의 메뉴 정보를 추천
    public List<String> getRecommendedMenuTypes() {
        // 조회수가 높은 상위 5개의 메뉴를 가져옴
        List<Menu> recommendedMenus = menuRepository.findTop10ByOrderByViewsDesc();

        // 메뉴 타입별로 조회수를 누적할 Map을 생성
        Map<String, Integer> menuTypeViewsMap = new HashMap<>();

        // 조회수 증가 로직을 포함하여 메뉴 타입별 조회수 누적
        for (Menu menu : recommendedMenus) {
            increaseMenuViews(menu); // 메뉴 조회 시 조회수 증가 로직 실행
            String menuType = menu.getMenuType(); // 메뉴의 타입 가져오기
            menuTypeViewsMap.put(menuType, menuTypeViewsMap.getOrDefault(menuType, 0) + menu.getViews());
            // 위에서 누적된 메뉴 타입별 조회수를 갱신 (누적 조회수에 현재 메뉴의 조회수를 더함)
        }

        // 누적된 메뉴 타입별 조회수를 기준으로 내림차순 정렬하여 상위 5개 메뉴 타입 추출
        List<String> recommendedMenuTypes = menuTypeViewsMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(10) // 상위 5개 메뉴 타입만 추출
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return recommendedMenuTypes; // 상위 5개 메뉴 타입 반환
    }

    // 메뉴 조회 시 조회수 증가 로직
    private void increaseMenuViews(Menu menu) {
        menu.setViews(menu.getViews() + 1); // 메뉴의 조회수 1 증가
    }


}
