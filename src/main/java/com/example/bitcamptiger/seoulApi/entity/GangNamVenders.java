package com.example.bitcamptiger.seoulApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gangNam_vendors")
@Getter
@Setter
public class GangNamVenders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String 연번;

    private String 관리부서전화번호;

    private String 관리부서명;

    private String 구분;

    private String 데이터기준일자;

    private String 소재지도로명주소;

    private String 소재지지번주소;

    private String 취급물품;
}
