package com.example.bitcamptiger.menu.dto;

import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.*;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDTO {
    private Long id;
    private String menuName;
    private int price;
    private String menuContent;
    private String menuSellStatus;

    private String MenuType;
    private int menuViews; // 조회수

    private String menuType;

    private Vendor vendor;

    private static ModelMapper modelMapper = new ModelMapper();

    //MenuDTO에서 Menu 엔티티로의 변환을 위해 ModelMapper 라이브러리 활용.
    public Menu createMenu(){
        return modelMapper.map(this, Menu.class);
    }

    public static MenuDTO of(Menu menu){
        return modelMapper.map(menu, MenuDTO.class);
    }
}
