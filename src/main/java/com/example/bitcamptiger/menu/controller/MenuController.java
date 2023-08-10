package com.example.bitcamptiger.menu.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    public final MenuService menuService;

    public final MenuRepository menuRepository;

    //모든 메뉴 정보 리스트
    @GetMapping("info/{vendorId}")
    public ResponseEntity<?> getMenuInfoList(@PathVariable Long vendorId){
        ResponseDTO<MenuDTO> response = new ResponseDTO<>();
        try{
            List<MenuDTO> menuDTOList = menuService.getMenuList(vendorId);

            response.setItems(menuDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    //메뉴 등록
    @PostMapping("info")
    public ResponseEntity<?> insertMenu(@RequestBody MenuDTO menuDTO){
        ResponseDTO<MenuDTO> response = new ResponseDTO<>();
        try{
            menuService.insertMenu(menuDTO);

            List<MenuDTO> menuDTOList = menuService.getMenuList(menuDTO.getVendor().getId());

            response.setItems(menuDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 메뉴 수정
    @PutMapping("info")
    public ResponseEntity<?> updateMenu(@RequestBody MenuDTO menuDTO){
        ResponseDTO<MenuDTO> response = new ResponseDTO<>();
        try{
            menuService.updateMenu(menuDTO);

            List<MenuDTO> menuDTOList = menuService.getMenuList(menuDTO.getVendor().getId());

            response.setItems(menuDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    // 메뉴 삭제
    @DeleteMapping("info")
    public ResponseEntity<?> deleteMenu(@RequestBody MenuDTO menuDTO){
        ResponseDTO<MenuDTO> response = new ResponseDTO<>();
        try{
            menuService.deleteMenu(menuDTO);

            List<MenuDTO> menuDTOList = menuService.getMenuList(menuDTO.getVendor().getId());

            response.setItems(menuDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

}