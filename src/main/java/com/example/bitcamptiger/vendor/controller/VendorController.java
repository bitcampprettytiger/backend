package com.example.bitcamptiger.vendor.controller;


import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.response.BaseResponse;
import com.example.bitcamptiger.response.BaseResponseStatus;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.example.bitcamptiger.response.BaseResponseStatus.*;

@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {

    public final VendorService vendorService;

    public final VendorRepository vendorRepository;

    public final MemberRepository memberRepository;

    @Operation(summary = "getVendorOpenInfoList", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/search")
    public BaseResponse<?> getVendorOpenInfoList(@RequestBody NowLocationDto nowLocationDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println(nowLocationDto);
        ResponseDTO<LocationDto> response = new ResponseDTO<>();
        try{
            // 로그인한 사용자의 정보에 접근 (필요한 경우)
            Member loggedInMember = customUserDetails.getUser();

            List<LocationDto> nowLocationList = vendorService.getNowLocationList(nowLocationDto);
            if(nowLocationList.isEmpty()){
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
//            BaseResponseStatus status = new BaseResponseStatus(INVALID_JWT);
//            BaseResponse<>
//            response.setStatusCode(FAIL_LOGIN_REFRESH.getCode());

            return new BaseResponse<>(POST_ISNULL);
        }

    }

    @Operation(summary = "getVendorOpenInfoList", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/search10vendor")
    public BaseResponse<?> getVendorrandmark(@RequestBody NowLocationDto nowLocationDto) {
        System.out.println(nowLocationDto);
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();
        try{
            List<VendorDTO> vendorDTOList = vendorService.giveLandmarkvendor(nowLocationDto);

            response.setItemlist(vendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return new BaseResponse<>(response);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            response.setErrorMessage(e.getMessage());

            return new BaseResponse<>(POST_ISNULL);
        }

    }

    @Operation(summary = "getVendorOpenInfoList", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/locationsave")
    public BaseResponse<?> saveVendorOpenInfoList(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody NowLocationDto nowLocationDto) {

        ResponseDTO<NowLocationDto> response = new ResponseDTO<>();

        try{
            // 로그인한 사용자의 정보에 접근 (필요한 경우)
            Member loggedInMember = customUserDetails.getUser();

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
            return new BaseResponse<>(FAIL_LOGIN_REFRESH);
        }

    }

    //현재 "OPEN" 가게 정보 리스트
    @Operation(summary = "openinfo", description = "가게 오픈 정보 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/openInfo")
    public ResponseEntity<?> getVendorOpenInfoList(@AuthenticationPrincipal CustomUserDetails customUserDetails,VendorDTO vendorDTO) {

        ResponseDTO<VendorDTO> response = new ResponseDTO<>();
        try{
            // 로그인한 사용자의 정보에 접근 (필요한 경우)
            Member loggedInMember = customUserDetails.getUser();

            List<VendorDTO> VendorDTOList = vendorService.getOpenList(vendorDTO.getVendorOpenStatus());

            response.setItemlist(VendorDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.FORBIDDEN.value());
            return ResponseEntity.badRequest().body(response);
        }

    }


    //모든 가게 정보 리스트
    @Operation(summary = "getVendorInfoList", description = "모든 가게 정보 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/info")
    public ResponseEntity<?> getVendorInfoList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();
        try{
            Member loggedInMember = customUserDetails.getUser();

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
    @Operation(summary = "getVendorByCategory", description = "카테고리로 검색하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/category")
    public ResponseEntity<?> getVendorByCategory(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String menuName,
            @RequestParam(required = false) String vendorName,
            @RequestParam(required = false, defaultValue = "vendorName") String orderBy){

        System.out.println(address);
        System.out.println(menuName);
        System.out.println(vendorName);
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();
        try{

            Member loggedInMember = customUserDetails.getUser();
            System.out.println(loggedInMember);
            List<VendorDTO> vendorDTOList = vendorService.getVendorByCategory(address, menuName, vendorName, orderBy);


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
    @Operation(summary = "getVendorByVendorType", description = "해당 타입에 포함되는 가게 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/vendorType/{vendorType}")
    public ResponseEntity<?> getVendorByVendorType(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String vendorType){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            Member loggedInMember = customUserDetails.getUser();
            System.out.println(loggedInMember);

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
    @Operation(summary = "getVendorByMenuType", description = "메뉴 타입별 가게 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/menuType/{menuType}")
    public ResponseEntity<?> getVendorByMenuType(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable String menuType){

        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            Member loggedInMember = customUserDetails.getUser();
            System.out.println(loggedInMember);
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

    //리뷰 100개 이상인 vendor 중 별점 높은 순 정렬
    @Operation(summary = "getVendorByReview", description = "리뷰 100개 이상인 vendor 중 별점 높은 순 정렬")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/review/averageReviewScore")
    public ResponseEntity<?> getVendorByReview(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            Member loggedInMember = customUserDetails.getUser();
            System.out.println(loggedInMember);

            List<VendorDTO> vendorDTOList = vendorService.getVendorByReview();

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
    @Operation(summary = "getVendorInfoDetail", description = "개별 상점 상세 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/infoDetail/{id}")
    public VendorDTO getVendorInfoDetail(@PathVariable Long id,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails){
        System.out.println("infoDetail");
        System.out.println(id);
        System.out.println(vendorService.getVendorDetail(id));
        return vendorService.getVendorDetail(id);
    }


    //토큰 안의 사용자 id에 해당되는 vendor 정보 가져오기
    @Operation(summary = "getVendorInfo", description = "사용자 id에 해당되는 vendor 정보 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/getVendorInfo")
    public ResponseEntity<?> getVendorInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<VendorDTO> response = new ResponseDTO();
        try{
            Member member = customUserDetails.getUser();
            Vendor vendor = vendorRepository.findByMember(member);
            response.setItem(VendorDTO.of(vendor));
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //신규 가게 등록

    @Operation(summary = "insertVendorInfo", description = "신규 가게 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/info")
    public ResponseEntity<?> insertVendorInfo(VendorDTO vendorDTO,
                                              @RequestParam(required = false, value = "file")MultipartFile[] uploadFiles,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails){
        System.out.println(vendorDTO);
        System.out.println(uploadFiles.length);
//        vendorDTO null 일때 vaildation
        if(vendorDTO.equals(null)){
            new BaseResponse<>(VENDORDTO_NUTNULL);
        }

        ResponseDTO<VendorDTO> response = new ResponseDTO<>();
        try{
            Optional<Member> byUsername = memberRepository.findByUsername(vendorDTO.getUsername());
//            Member member = customUserDetails.getUser();

            Optional<MultipartFile[]> optionalUploadFiles = Optional.ofNullable(uploadFiles);
            if (optionalUploadFiles.isPresent()) {
                System.out.println("if");
                MultipartFile[] actualUploadFiles = optionalUploadFiles.get();
                vendorService.insertVendor(byUsername.get(), vendorDTO, actualUploadFiles);
            } else {
                System.out.println("else");
                MultipartFile[] actualUploadFiles = null;
                vendorService.insertVendor(byUsername.get(), vendorDTO, actualUploadFiles);
            }

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
    @Operation(summary = "updateVendorInfo", description = "가게 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PutMapping("/info")
    public ResponseEntity<?> updateVendorInfo(VendorDTO vendorDTO, @RequestParam(required = false, value = "file")MultipartFile[] uploadFiles,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            Member member = customUserDetails.getUser();

            vendorService.updateVendor(member, vendorDTO, uploadFiles);

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
    @Operation(summary = "deleteVendorInfo", description = "가게 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @DeleteMapping("/info")
    public ResponseEntity<?> deleteVendorInfo(VendorDTO vendorDTO,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<VendorDTO> response = new ResponseDTO<>();

        try{
            Member member = customUserDetails.getUser();
            vendorService.deleteVendor(member, vendorDTO);

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
