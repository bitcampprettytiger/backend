package com.example.bitcamptiger.menu.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuId implements Serializable {
    //IdClass에서 참조할 키 변수명은 원본 엔티티의 키 변수명과 일치시킨다.
    private Long id;        //menu 엔티티의 키 값
    private Long vendor;    //menu 엔티티의  Vendor vendor
}
