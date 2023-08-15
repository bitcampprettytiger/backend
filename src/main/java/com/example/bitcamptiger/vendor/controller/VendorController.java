package com.example.bitcamptiger.vendor.controller;


import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.response.BaseResponse;
import com.example.bitcamptiger.response.BaseResponseStatus;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.example.bitcamptiger.vendor.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bitcamptiger.response.BaseResponseStatus.VENDORDTO_NUTNULL;

@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {

    public final VendorService vendorService;

    public final VendorRepository vendorRepository;

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


    //개별 상점 상세 정보 확인
    @GetMapping("/infoDetail/{id}")
    public Vendor getVendorInfoDetail(@PathVariable Long id){
        return vendorService.getVendorDetail(id);
    }


    //신규 가게 등록
    @PostMapping("/info")
    public ResponseEntity<?> insertVendorInfo(@RequestBody VendorDTO vendorDTO){
        System.out.println(vendorDTO);
//        vendorDTO null 일때 vaildation
        if(vendorDTO.equals(null)){
            new BaseResponse<>(VENDORDTO_NUTNULL);
        }
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
