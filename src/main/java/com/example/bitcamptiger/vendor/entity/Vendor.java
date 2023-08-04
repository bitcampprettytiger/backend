package com.example.bitcamptiger.vendor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vendor {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String vendorType;

    @Column
    private String vendorName;

    @Column
    @Enumerated(EnumType.STRING)
    private VendorOpenStatus vendorOpenStatus;

    @Column
    private String address;

    //좌표(위도)
    @Column
    private String x;

    //좌표(경도)
    @Column
    private String y;

    @Column
    private String tel;

    @Column
    private String businessDay;

    @Column
    private LocalTime open;

    @Column
    private LocalTime close;

    @Column
    private String menu;

    //사업자 번호
    @Column
    private  String b_no;

    //도로 점유 허가번호
    @Column
    private String perNo;

    //실제 신청자명
    @Column
    private String rlAppiNm;

}
