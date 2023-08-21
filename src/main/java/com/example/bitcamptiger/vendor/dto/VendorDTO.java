package com.example.bitcamptiger.vendor.dto;


import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {
    private Long id;
    private String vendorType;
    private String vendorName;
    private String vendorOpenStatus;
    private String address;
    private String tel;
    private String businessDay;
    private String open;
    private String close;
    private String x;
    private String y;
    private String b_no;        //사업자 번호
    private String perNo;       //도로 점유증 허가 번호
    private String rlAppiNm;        //신청인명

    private String location;
    private Double totalReviewScore;
    private Long reviewCount;
    private Double weightedAverageScore;

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

