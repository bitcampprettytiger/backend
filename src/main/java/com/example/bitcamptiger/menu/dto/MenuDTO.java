package com.example.bitcamptiger.menu.dto;

import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.modelmapper.ModelMapper;

import java.util.List;


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

    private String menuType;



    //메뉴 조회수
    private int views;

    private Vendor vendor;


    private List<MenuImageDTO> menuImageList;

    private static ModelMapper modelMapper = new ModelMapper();

    //MenuDTO에서 Menu 엔티티로의 변환을 위해 ModelMapper 라이브러리 활용.
    public Menu createMenu(){
        return modelMapper.map(this, Menu.class);
    }



    public static MenuDTO of(Menu menu){
        return modelMapper.map(menu, MenuDTO.class);
    }
}
