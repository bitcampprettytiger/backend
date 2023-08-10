package com.example.bitcamptiger.menu.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private Long id;
    private String menuName;
    private int price;
    private String menuContent;
    private String menuSellStatus;
    private String menuType;

}
