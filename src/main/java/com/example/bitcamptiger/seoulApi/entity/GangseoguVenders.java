package com.example.bitcamptiger.seoulApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gangseogu_vendors")
@Getter
@Setter
public class GangseoguVenders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String 거리가게_유형;
    private String 데이터_기준일자;
    private String 비고;
    private String 시군구명;
    private String 위치;
    private String 판매품목;
    private String 허가기간;



}
