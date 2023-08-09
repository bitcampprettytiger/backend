package com.example.bitcamptiger.menu.dto;

import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private Vendor vendor;

    private static ModelMapper modelMapper = new ModelMapper();

    public Menu createMenu(){
        return modelMapper.map(this, Menu.class);
    }

    public static MenuDTO of(Menu menu){
        return modelMapper.map(menu, MenuDTO.class);
    }
}
