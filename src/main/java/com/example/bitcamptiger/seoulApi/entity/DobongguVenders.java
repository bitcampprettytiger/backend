package com.example.bitcamptiger.seoulApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dobonggu_vendors")
@Getter
@Setter
public class DobongguVenders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String 도로주소;
    private String 연번;
    private String 운영품목;
    private String 점용목적;
    private String 점용장소;
    private String 지정번호;

}
