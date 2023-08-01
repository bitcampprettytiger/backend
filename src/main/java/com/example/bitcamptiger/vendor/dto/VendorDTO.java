package com.example.bitcamptiger.vendor.dto;


import com.example.bitcamptiger.board.dto.BoardDto;
import com.example.bitcamptiger.board.entity.Board;
import com.example.bitcamptiger.vendor.entity.BusinessDay;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.entity.VendorOpenStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {
    private Long id;
    private String vendorType;
    private String vendorName;
    private String vendorOpenStatus;  //String 타입으로 받기
    private String address;
//    private double lat;
//    private double lon;
    private String tel;
    private String businessDay;  //String 타입으로 받기
    private LocalTime open;  //String 타입으로 받기
    private LocalTime close;  //String 타입으로 받기
    private String menu;

    private static ModelMapper modelMapper = new ModelMapper();

    public Vendor createVendor(){
        return modelMapper.map(this, Vendor.class);
    }

    public static VendorDTO of(Vendor vendor){
        return modelMapper.map(vendor, VendorDTO.class);
    }

}

