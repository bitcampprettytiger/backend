package com.example.bitcamptiger.gyeongGiApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gyeonggi_vendors")
@Getter
@Setter
public class GyeonggiVenders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prmsnNm;
    private String refineLotnoAddr;
    private String refineZipno;
    private String induTypeNm;
    private String licensgDe;
    private String sigunNm;
    private double occupTnAr;
    private String refineRoadnmAddr;
    private String refineWgs84Lat;
    private String storeNm;
    private String clsbizYn;
    private String refineWgs84Logt;
}
