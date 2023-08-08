package com.example.bitcamptiger.vendor.entity;

import com.example.bitcamptiger.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vendor {


    @Id
<<<<<<< HEAD
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "VendorSeqGenerator")
    @Column(name = "vendor_id")
=======
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
>>>>>>> 4b4a551369b574f3bf3219f602b51bb007d02c9a
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
    @Enumerated(EnumType.STRING)
    private BusinessDay businessDay;

    @Column
    private LocalTime open;

    @Column
    private LocalTime close;

    @Column
    private String menu;

    @Column
    private String b_no;        //사업자 번호

    @Column
    private String perNo;       //도로 점유증 허가 번호

    @Column
    private String rlAppiNm;        //신청인명

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Menu> menulist;


}
