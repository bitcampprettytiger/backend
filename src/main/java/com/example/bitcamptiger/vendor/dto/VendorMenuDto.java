package com.example.bitcamptiger.vendor.dto;


import com.example.bitcamptiger.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorMenuDto {

//   menu 리스트
    private List<Menu> menu;
//    난방시설 정보
    private String cooller;

//    줄서기 예약
    private boolean roll;
//    포장 예약
    private boolean takeout;

}
