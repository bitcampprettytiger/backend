package com.example.bitcamptiger.seoulApi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dongJak_vendors")
@Getter
@Setter
public class DongJakVenders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int 연번;
    private String 구분;
    private String 거리가게명;
    private String 업종_판매품목;
    private String 위치;
    private String 데이터기준일자;

}
