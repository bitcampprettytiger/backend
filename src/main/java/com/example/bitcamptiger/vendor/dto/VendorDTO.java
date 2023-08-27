package com.example.bitcamptiger.vendor.dto;


import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;






@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {
    private Long id;
//  구분(노점 포장마차)
    private String vendorType;
//  가게이름
    private String vendorName;
//  시그니처메뉴(대표메뉴)
    private String SIGMenu;

    private String vendorOpenStatus;
//  가게주소 (도로명)
    private String address;
//   가게번호
    private String tel;
//  영업일
    private String businessDay;
// 영업오픈시간
    private String open;
// 영업닫는시간
    private String close;
//사업자 번호
//    private String b_no;
//도로 점유증 허가 번호
//    private String perNo;
// 신청인명
//private String rlAppiNm;

    private String primaryimgurl;

    private String x;
    private String y;
    private String location;

    private Double totalReviewScore;
    private Long reviewCount;
    private Double averageReviewScore;

    private List<VendorImageDTO> vendorImageDTOList;

    private List<MenuDTO> menuDTOList;


    private static ModelMapper modelMapper = new ModelMapper();


    public Vendor createVendor(){
        return modelMapper.map(this, Vendor.class);
    }

    public static VendorDTO of(Vendor vendor){
        return modelMapper.map(vendor, VendorDTO.class);
    }

}
// 길벗가게 개업 -> 사업자 번호 x(있어야 될듯) (리스폰스 사업자 주소,사업자 번호=> 리스폰스)

// 사업자등록증 ->
