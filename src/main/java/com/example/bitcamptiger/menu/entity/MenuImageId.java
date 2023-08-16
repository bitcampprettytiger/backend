package com.example.bitcamptiger.menu.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuImageId implements Serializable {


    private Menu menu;

    private Long id;    //MenuImage의 id 매핑
}
