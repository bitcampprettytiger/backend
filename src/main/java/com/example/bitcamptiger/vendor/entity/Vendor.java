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
@SequenceGenerator(
        name = "VendorSeqGenerator",
        sequenceName = "VENDOR_SEQ", // 시퀀스 이름을 대문자로 지정
        initialValue = 1,
        allocationSize = 1
)
public class Vendor {


    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "VendorSeqGenerator")
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



}
