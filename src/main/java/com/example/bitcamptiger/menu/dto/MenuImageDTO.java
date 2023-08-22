package com.example.bitcamptiger.menu.dto;

import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuImageDTO {
    private Long id;
    private String fileName;
    private String url;
    private String originName;

    private static ModelMapper modelMapper = new ModelMapper();

    public Menu createMenuImage(){
        return modelMapper.map(this, Menu.class);
    }

    public static MenuImageDTO of(MenuImage menuImage){
        return modelMapper.map(menuImage, MenuImageDTO.class);
    }
}
