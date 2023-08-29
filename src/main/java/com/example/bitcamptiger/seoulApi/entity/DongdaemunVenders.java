package com.example.bitcamptiger.seoulApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dongdaemun_vendors")
@Getter
@Setter
public class DongdaemunVenders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String 연번;
    private String 거리가게명;
    private String 업종;
    private String 주소;
    private String 데이터수집일자;
}
