package com.example.bitcamptiger.vendor.controller;


import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.response.BaseResponse;
import com.example.bitcamptiger.vendor.dto.LocationDto;
import com.example.bitcamptiger.vendor.dto.NowLocationDto;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.example.bitcamptiger.vendor.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.bitcamptiger.response.BaseResponseStatus.*;

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
                System.out.println("테스트");
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

    @Operation(summary = "openinfo", description = "가게오픈정보 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
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

    @Operation(summary = "postinfo", description = "가게 등록하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/info")
    public ResponseEntity<?> insertVendorInfo(VendorDTO vendorDTO, @RequestParam(required = false, value = "file")MultipartFile[] uploadFiles){
        System.out.println(vendorDTO);
//        vendorDTO null 일때 vaildation
        if(vendorDTO.equals(null)){
            new BaseResponse<>(VENDORDTO_NUTNULL);
        }
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            vendorService.insertVendor(vendorDTO, uploadFiles);

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
    public ResponseEntity<?> updateVendorInfo(VendorDTO vendorDTO, @RequestParam(required = false, value = "file")MultipartFile[] uploadFiles){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            vendorService.updateVendor(vendorDTO, uploadFiles);

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
