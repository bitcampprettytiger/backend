package com.example.bitcamptiger.menu.controller;

import com.example.bitcamptiger.common.service.S3UploadService;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.menu.service.Impl.MenuServiceImpl;
import com.example.bitcamptiger.menu.service.MenuService;
import com.example.bitcamptiger.vendor.dto.VendorMenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    public  final S3UploadService s3UploadService;
    public final MenuService menuService;

    public final MenuRepository menuRepository;
    public final MenuServiceImpl menuServiceImpl;



    //해당 가게의 모든 메뉴 정보 리스트
    @GetMapping("/info/{vendorId}")
    public ResponseEntity<?> getMenuInfoList(@PathVariable Long vendorId){
        ResponseDTO<MenuDTO> response = new ResponseDTO<>();
        try{
            List<MenuDTO> menuDTOList = menuService.getMenuList(vendorId);



            response.setItemlist(menuDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    //메뉴 등록
    @PostMapping("/info/insertMenu")
    public ResponseEntity<?> insertMenu(@AuthenticationPrincipal CustomUserDetails customUserDetails, MenuDTO menuDTO, @RequestParam(required = false, value = "file") MultipartFile[] uploadFiles){
        ResponseDTO<MenuDTO> response = new ResponseDTO<>();

        try{
            // 로그인한 사용자의 정보에 접근
            Member loggedInMember = customUserDetails.getUser();

            menuService.insertMenu(loggedInMember,menuDTO, uploadFiles);

            List<MenuDTO> menuDTOList = menuService.getMenuList(menuDTO.getVendor().getId());

            response.setItemlist(menuDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



    @PostMapping("/UpdateVendor")
    public ResponseEntity<?> vendorupdateMenu( @AuthenticationPrincipal CustomUserDetails customUserDetails,VendorMenuDto vendorMenuDto){
        ResponseDTO<String> response = new ResponseDTO<>();
        System.out.println(vendorMenuDto);

        try{
            // 로그인한 사용자의 정보에 접근
            Member loggedInMember = customUserDetails.getUser();

            response.setItem("수정완료");
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }
    // 메뉴 수정
    @PutMapping("/info/changeMenu")
    public ResponseEntity<?> updateMenu(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,MenuDTO menuDTO,
            @RequestParam(required = false, value = "file") MultipartFile[] uploadFiles){
        ResponseDTO<MenuDTO> response = new ResponseDTO<>();
        try{
            // 로그인한 사용자의 정보에 접근
            Member loggedInMember = customUserDetails.getUser();

            menuService.updateMenu(loggedInMember, menuDTO, uploadFiles);

            List<MenuDTO> menuDTOList = menuService.getMenuList(menuDTO.getVendor().getId());

            response.setItemlist(menuDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



    // 메뉴 삭제
    @DeleteMapping("/info/deleteMenu")
    public ResponseEntity<?> deleteMenu(MenuDTO menuDTO){
        ResponseDTO<MenuDTO> response = new ResponseDTO<>();
        try{
            menuService.deleteMenu(menuDTO);

            List<MenuDTO> menuDTOList = menuService.getMenuList(menuDTO.getVendor().getId());

            response.setItemlist(menuDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    //추천 메뉴 표출
    //조회수가 가장 많은 탑 5 메뉴를 표시할 것
    @GetMapping("/recommendedMenus5")
    public List<String> getRecommendedMenuTypes() {
        return menuService.getRecommendedMenuTypes();
    }
}
