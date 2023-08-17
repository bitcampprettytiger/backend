package com.example.bitcamptiger.vendor.controller;


import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.response.BaseResponse;
import com.example.bitcamptiger.vendor.dto.LocationDto;
import com.example.bitcamptiger.vendor.dto.NowLocationDto;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.example.bitcamptiger.vendor.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bitcamptiger.response.BaseResponseStatus.POST_ISNULL;
import static com.example.bitcamptiger.response.BaseResponseStatus.RESPONSE_ERROR;

@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {

    public final VendorService vendorService;

    public final VendorRepository vendorRepository;



    @PostMapping("/search")
    public BaseResponse<?> getVendorOpenInfoList(@RequestBody NowLocationDto nowLocationDto) {
        System.out.println(nowLocationDto);
        ResponseDTO<LocationDto> response = new ResponseDTO<>();
        try{
            List<LocationDto> nowLocationList = vendorService.getNowLocationList(nowLocationDto);
            if(nowLocationList.isEmpty()){
                System.out.println("null");
               return new BaseResponse<>(RESPONSE_ERROR);
            }
            System.out.println("????????");
//            List<VendorDTO> VendorDTOList = vendorService.getOpenList(vendorDTO.getVendorOpenStatus());

            response.setItemlist(nowLocationList);
            response.setStatusCode(HttpStatus.OK.value());

            return new BaseResponse<>(response);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new BaseResponse<>(response);
        }

    }
    @PostMapping("/locationsave")
    public BaseResponse<?> saveVendorOpenInfoList(@RequestBody NowLocationDto nowLocationDto) {

        ResponseDTO<NowLocationDto> response = new ResponseDTO<>();

        try{
            NowLocationDto saverandmark = vendorService.saverandmark(nowLocationDto);
            if(saverandmark.equals(null)){
                return new BaseResponse<>(POST_ISNULL);
            }
//            List<VendorDTO> VendorDTOList = vendorService.getOpenList(vendorDTO.getVendorOpenStatus());

            response.setItem(saverandmark);
            response.setStatusCode(HttpStatus.OK.value());

            return new BaseResponse<>(response);
        } catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new BaseResponse<>(response);
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


    //open상태, 지역별로 카테고리화 조회
    @GetMapping("/category/{address}")
    public ResponseEntity<?> getVendorByAddressCategory(
            @PathVariable String address){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            List<VendorDTO> vendorDTOList = vendorService.getVendorByAddressCategory(address);

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
