package com.example.bitcamptiger.vendor.controller;


import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.example.bitcamptiger.vendor.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {

    public final VendorService vendorService;

    public final VendorRepository vendorRepository;



    @GetMapping("/search")
    public ResponseEntity<?> getVendorOpenInfoList() {

        ResponseDTO<VendorDTO> response = new ResponseDTO<>();
        try{

//            List<VendorDTO> VendorDTOList = vendorService.getOpenList(vendorDTO.getVendorOpenStatus());

//            response.setItemlist();
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

    }


    //현재 "OPEN" 가게 정보 리스트
    @GetMapping("/openInfo")
    public ResponseEntity<?> getVendorOpenInfoList(VendorDTO vendorDTO) {

        ResponseDTO<VendorDTO> response = new ResponseDTO<>();
        try{

            List<VendorDTO> VendorDTOList = vendorService.getOpenList(vendorDTO.getVendorOpenStatus());

            response.setItemlist(VendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

    }


    //모든 가게 정보 리스트
    @GetMapping("/info")
    public ResponseEntity<?> getVendorInfoList(){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();
        try{

            List<VendorDTO> vendorDTOList = vendorService.getVendorList();

            response.setItemlist(vendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    //지역명으로 검색
    //메뉴명으로 검색
    //가게명으로 검색
    @GetMapping("/category")
    public ResponseEntity<?> getVendorByCategory(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String menuName,
            @RequestParam(required = false) String vendorName){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            List<VendorDTO> vendorDTOList = vendorService.getVendorByCategory(address, menuName, vendorName);

            response.setItemlist(vendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

    }


    //길거리 음식, 포장마차 타입 분류
    //해당 타입에 포함되는 가게 조회하기
    @GetMapping("/vendorType/{vendorType}")
    public ResponseEntity<?> getVendorByVendorType(
            @PathVariable String vendorType){

        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            List<VendorDTO> vendorDTOList = vendorService.getVendorByVendorType(vendorType);

            response.setItemlist(vendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



    //메뉴 타입별 가게 정보 조회

    @GetMapping("/menuType/{menuType}")
    public ResponseEntity<?> getVendorByMenuType(
            @PathVariable String menuType){

        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            List<VendorDTO> vendorDTOList = vendorService.getVendorByMenuType(menuType);

            response.setItemlist(vendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



    //개별 상점 상세 정보 확인
    @GetMapping("/infoDetail/{id}")
    public Vendor getVendorInfoDetail(@PathVariable Long id){
        return vendorService.getVendorDetail(id);
    }


    //신규 가게 등록
    @PostMapping("/info")
    public ResponseEntity<?> insertVendorInfo(@RequestBody VendorDTO vendorDTO){
        System.out.println(vendorDTO);
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();
        try{
            vendorService.insertVendor(vendorDTO);

            List<VendorDTO> vendorDTOList = vendorService.getVendorList();

            response.setItemlist(vendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

    }


    //가게 정보 수정
    @PutMapping("/info")
    public ResponseEntity<?> updateVendorInfo(@RequestBody VendorDTO vendorDTO){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            vendorService.updateVendor(vendorDTO);

            List<VendorDTO> vendorDTOList = vendorService.getVendorList();


            response.setItemlist(vendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

    }


    //가게 정보 삭제
    @DeleteMapping("/info")
    public ResponseEntity<?> deleteVendorInfo(@RequestBody VendorDTO vendorDTO){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            vendorService.deleteVendor(vendorDTO);

            List<VendorDTO> vendorDTOList = vendorService.getVendorList();


            response.setItemlist(vendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }






}
