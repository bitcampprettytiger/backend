package com.example.bitcamptiger.menu.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuImageId implements Serializable {

    //private MenuId menuId;  //MenuImage의 menu 매핑

    @Column(name = "menu_id")
    private Menu menu;

    @Column(name = "menu_img_id")
    private Long id;    //MenuImage의 id 매핑
}
