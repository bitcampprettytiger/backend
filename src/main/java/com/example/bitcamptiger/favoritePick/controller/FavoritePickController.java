package com.example.bitcamptiger.favoritePick.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.favoritePick.DTO.FavoriteVendorDTO;
import com.example.bitcamptiger.favoritePick.service.FavoriteService;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/favoritePick")
public class FavoritePickController {

    private final FavoriteService favoriteService;
    private final VendorService vendorService;

    @Autowired
    public FavoritePickController(FavoriteService favoriteService, VendorService vendorService) {
        this.favoriteService = favoriteService;
        this.vendorService = vendorService;
    }

    //해당 유저와 가게 정보를 받아와서 찜하기를 추가
    @PostMapping("/add/{vendorId}")
    public void addFavorite(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long vendorId) {

        Member member = new Member();
        member.setId(customUserDetails.getUser().getId());

        Vendor vendor = favoriteService.getVendorById(vendorId);

        favoriteService.addFavoriteVendor(member, vendor);
    }

    //유저와 가게 정보를 받아와서 찜하기를 삭제
    @DeleteMapping("/{memberId}/remove/{vendorId}")
    public void removeFavorite(@PathVariable Long memberId, @PathVariable Long vendorId) {
        Member member = new Member();
        member.setId(memberId);

        Vendor vendor = favoriteService.getVendorById(vendorId);

        favoriteService.removeFavoriteVendor(member, vendor);
    }

    //찜하기 많은 수 를 가진 가게를 탑 8 으로 표출할 수 있는 컨트롤러
    @GetMapping("/top8Favorites")
    public List<VendorDTO> getTop8FavoriteVendors() {
        List<Vendor> top8Vendors = favoriteService.getTop8FavoriteVendors();

        List<VendorDTO> top8VendorDTOs = new ArrayList<>();
        for (Vendor vendor : top8Vendors) {
            VendorDTO vendorDTO = vendorService.getVendorDetail(vendor.getId());
            top8VendorDTOs.add(vendorDTO);
        }

        return top8VendorDTOs;
    }


    //내 찜하기 리스트 조회하기
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMyFavoriteVendor(@PathVariable Long memberId){
        ResponseDTO<FavoriteVendorDTO> response = new ResponseDTO<>();

        try{

//            List<FavoriteVendorDTO> favoriteVendorDTOList = favoriteService.getMyFavoriteVendor(memberId);

            return  null;
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
